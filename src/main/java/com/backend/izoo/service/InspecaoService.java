package com.backend.izoo.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.backend.izoo.dto.InspecaoDTO;
import com.backend.izoo.model.Inspecao;
import com.backend.izoo.model.InspecaoDeletado;
import com.backend.izoo.repositories.InspecaoRepository;
import com.backend.izoo.repositories.InspecaoDeletadoRepository;

import jakarta.validation.Valid;

@Service
public class InspecaoService {

    @Autowired
    private InspecaoRepository inspecaoRepository;

    @Autowired
    private InspecaoDeletadoRepository inspecaoDeletadoRepository;

    public InspecaoDTO buscarPorId(String id) {
        Optional<Inspecao> inspecao = inspecaoRepository.findById(id);
        if (inspecao.isPresent()) {
            return new InspecaoDTO(inspecao.get());
        }
        throw new RuntimeException("Inspeção não encontrada com id: " + id);
    }

    public List<InspecaoDTO> buscarTodos() {
        List<Inspecao> lista = inspecaoRepository.findAll();
        return lista.stream().map(InspecaoDTO::new).toList();
    }

    public InspecaoDTO inserir(@Valid InspecaoDTO inspecaoDTO) {
        Inspecao entidade = new Inspecao();
        copiaDTOparaEntidade(inspecaoDTO, entidade);
        entidade.setCreatedAt(Instant.now());
        entidade.setUpdatedAt(Instant.now());
        entidade = inspecaoRepository.save(entidade);
        return new InspecaoDTO(entidade);
    }

    public InspecaoDTO atualizar(String id, @Valid InspecaoDTO inspecaoDTO) {
        Optional<Inspecao> inspecaoOpt = inspecaoRepository.findById(id);
        if (inspecaoOpt.isPresent()) {
            Inspecao entidade = inspecaoOpt.get();
            copiaDTOparaEntidade(inspecaoDTO, entidade);
            entidade.setUpdatedAt(Instant.now());
            entidade = inspecaoRepository.save(entidade);
            return new InspecaoDTO(entidade);
        }
        throw new RuntimeException("Inspeção não encontrada com id: " + id);
    }

    public InspecaoDTO atualizarParcial(String id, @Valid InspecaoDTO inspecaoDTO) {
        Optional<Inspecao> inspecaoOpt = inspecaoRepository.findById(id);
        if (inspecaoOpt.isPresent()) {
            Inspecao entidade = inspecaoOpt.get();
            copiaDTOparaEntidadeParcial(inspecaoDTO, entidade);
            entidade.setUpdatedAt(Instant.now());
            entidade = inspecaoRepository.save(entidade);
            return new InspecaoDTO(entidade);
        }
        throw new RuntimeException("Inspeção não encontrada com id: " + id);
    }

    /**
     * Soft delete - Move inspeção para coleção de backup antes de deletar
     */
    public void deletar(String id) {
        Optional<Inspecao> inspecaoOpt = inspecaoRepository.findById(id);
        if (inspecaoOpt.isPresent()) {
            Inspecao inspecao = inspecaoOpt.get();
            
            // Obter usuário atual que está fazendo a deleção
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String deletedBy = auth != null ? auth.getName() : "System";
            
            // Criar backup da inspeção
            InspecaoDeletado inspecaoDeletado = new InspecaoDeletado(
                inspecao, 
                deletedBy, 
                "Inspeção deletada via API"
            );
            
            // Salvar no backup
            inspecaoDeletadoRepository.save(inspecaoDeletado);
            
            // Deletar da coleção principal
            inspecaoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Inspeção não encontrada com id: " + id);
        }
    }

    /**
     * Buscar histórico de inspeções deletadas (apenas para admins)
     */
    public List<InspecaoDeletado> buscarInspecoesDeletadas() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream()
            .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
        
        if (!isAdmin) {
            throw new RuntimeException("Apenas administradores podem acessar histórico de inspeções deletadas");
        }
        
        return inspecaoDeletadoRepository.findAllOrderByDeletedAtDesc();
    }

    public List<InspecaoDTO> buscarPorTipo(String tipo) {
        List<Inspecao> lista = inspecaoRepository.findByTipoIgnoreCase(tipo);
        return lista.stream().map(InspecaoDTO::new).toList();
    }

    public List<InspecaoDTO> buscarPorBairro(String bairro) {
        List<Inspecao> lista = inspecaoRepository.findByBairroIgnoreCase(bairro);
        return lista.stream().map(InspecaoDTO::new).toList();
    }

    public List<InspecaoDTO> buscarPorGravidade(String gravidade) {
        List<Inspecao> lista = inspecaoRepository.findByGravidadeIgnoreCase(gravidade);
        return lista.stream().map(InspecaoDTO::new).toList();
    }

    public List<InspecaoDTO> buscarPorStatus(String status) {
        List<Inspecao> lista = inspecaoRepository.findByStatusIgnoreCase(status);
        return lista.stream().map(InspecaoDTO::new).toList();
    }

    public List<InspecaoDTO> buscarPorBairroEStatus(String bairro, String status) {
        List<Inspecao> lista = inspecaoRepository.findByBairroIgnoreCaseAndStatusIgnoreCase(bairro, status);
        return lista.stream().map(InspecaoDTO::new).toList();
    }

    private void copiaDTOparaEntidade(InspecaoDTO inspecaoDTO, Inspecao entidade) {
        entidade.setTipo(inspecaoDTO.getTipo());
        entidade.setBairro(inspecaoDTO.getBairro());
        entidade.setGravidade(inspecaoDTO.getGravidade());
        entidade.setStatus(inspecaoDTO.getStatus());
    }

    private void copiaDTOparaEntidadeParcial(InspecaoDTO inspecaoDTO, Inspecao entidade) {
        if (inspecaoDTO.getTipo() != null && !inspecaoDTO.getTipo().trim().isEmpty()) {
            entidade.setTipo(inspecaoDTO.getTipo());
        }
        if (inspecaoDTO.getBairro() != null && !inspecaoDTO.getBairro().trim().isEmpty()) {
            entidade.setBairro(inspecaoDTO.getBairro());
        }
        if (inspecaoDTO.getGravidade() != null && !inspecaoDTO.getGravidade().trim().isEmpty()) {
            entidade.setGravidade(inspecaoDTO.getGravidade());
        }
        if (inspecaoDTO.getStatus() != null && !inspecaoDTO.getStatus().trim().isEmpty()) {
            entidade.setStatus(inspecaoDTO.getStatus());
        }
    }
}
