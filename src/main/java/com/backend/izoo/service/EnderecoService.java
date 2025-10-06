package com.backend.izoo.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.izoo.dto.EnderecoDTO;
import com.backend.izoo.model.Endereco;
import com.backend.izoo.repositories.EnderecoRepository;

import jakarta.validation.Valid;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    public EnderecoDTO buscarPorId(String id) {
        Optional<Endereco> endereco = enderecoRepository.findById(id);
        if (endereco.isPresent()) {
            return new EnderecoDTO(endereco.get());
        }
        throw new RuntimeException("Endereço não encontrado com id: " + id);
    }

    public List<EnderecoDTO> buscarTodos() {
        List<Endereco> lista = enderecoRepository.findAll();
        return lista.stream().map(EnderecoDTO::new).toList();
    }

    public EnderecoDTO inserir(@Valid EnderecoDTO enderecoDTO) {
        Endereco entidade = new Endereco();
        copiaDTOparaEntidade(enderecoDTO, entidade);
        entidade.setCreatedAt(Instant.now());
        entidade.setUpdatedAt(Instant.now());
        entidade = enderecoRepository.save(entidade);
        return new EnderecoDTO(entidade);
    }

    public EnderecoDTO atualizar(String id, @Valid EnderecoDTO enderecoDTO) {
        Optional<Endereco> enderecoOpt = enderecoRepository.findById(id);
        if (enderecoOpt.isPresent()) {
            Endereco entidade = enderecoOpt.get();
            copiaDTOparaEntidade(enderecoDTO, entidade);
            entidade.setUpdatedAt(Instant.now());
            entidade = enderecoRepository.save(entidade);
            return new EnderecoDTO(entidade);
        }
        throw new RuntimeException("Endereço não encontrado com id: " + id);
    }

    public EnderecoDTO atualizarParcial(String id, @Valid EnderecoDTO enderecoDTO) {
        Optional<Endereco> enderecoOpt = enderecoRepository.findById(id);
        if (enderecoOpt.isPresent()) {
            Endereco entidade = enderecoOpt.get();
            copiaDTOparaEntidadeParcial(enderecoDTO, entidade);
            entidade.setUpdatedAt(Instant.now());
            entidade = enderecoRepository.save(entidade);
            return new EnderecoDTO(entidade);
        }
        throw new RuntimeException("Endereço não encontrado com id: " + id);
    }

    public void deletar(String id) {
        if (enderecoRepository.existsById(id)) {
            enderecoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Endereço não encontrado com id: " + id);
        }
    }

    public List<EnderecoDTO> buscarPorCidade(String cidade) {
        List<Endereco> lista = enderecoRepository.findByCidadeIgnoreCase(cidade);
        return lista.stream().map(EnderecoDTO::new).toList();
    }

    public List<EnderecoDTO> buscarPorEstado(String estado) {
        List<Endereco> lista = enderecoRepository.findByEstadoIgnoreCase(estado);
        return lista.stream().map(EnderecoDTO::new).toList();
    }

    public List<EnderecoDTO> buscarPorCidadeEEstado(String cidade, String estado) {
        List<Endereco> lista = enderecoRepository.findByCidadeIgnoreCaseAndEstadoIgnoreCase(cidade, estado);
        return lista.stream().map(EnderecoDTO::new).toList();
    }

    public List<EnderecoDTO> buscarComLocalizacao() {
        List<Endereco> lista = enderecoRepository.findByLatitudeAndLongitudeNotNull();
        return lista.stream().map(EnderecoDTO::new).toList();
    }

    private void copiaDTOparaEntidade(EnderecoDTO enderecoDTO, Endereco entidade) {
        entidade.setRua(enderecoDTO.getRua());
        entidade.setNumero(enderecoDTO.getNumero());
        entidade.setBairro(enderecoDTO.getBairro());
        entidade.setCidade(enderecoDTO.getCidade());
        entidade.setEstado(enderecoDTO.getEstado());
        entidade.setCep(enderecoDTO.getCep());
        entidade.setLatitude(enderecoDTO.getLatitude());
        entidade.setLongitude(enderecoDTO.getLongitude());
    }

    private void copiaDTOparaEntidadeParcial(EnderecoDTO enderecoDTO, Endereco entidade) {
        if (enderecoDTO.getRua() != null && !enderecoDTO.getRua().trim().isEmpty()) {
            entidade.setRua(enderecoDTO.getRua());
        }
        if (enderecoDTO.getNumero() != null && !enderecoDTO.getNumero().trim().isEmpty()) {
            entidade.setNumero(enderecoDTO.getNumero());
        }
        if (enderecoDTO.getBairro() != null && !enderecoDTO.getBairro().trim().isEmpty()) {
            entidade.setBairro(enderecoDTO.getBairro());
        }
        if (enderecoDTO.getCidade() != null && !enderecoDTO.getCidade().trim().isEmpty()) {
            entidade.setCidade(enderecoDTO.getCidade());
        }
        if (enderecoDTO.getEstado() != null && !enderecoDTO.getEstado().trim().isEmpty()) {
            entidade.setEstado(enderecoDTO.getEstado());
        }
        if (enderecoDTO.getCep() != null && !enderecoDTO.getCep().trim().isEmpty()) {
        	entidade.setCep(enderecoDTO.getCep());
        }
        if (enderecoDTO.getLatitude() != null) {
            entidade.setLatitude(enderecoDTO.getLatitude());
        }
        if (enderecoDTO.getLongitude() != null) {
            entidade.setLongitude(enderecoDTO.getLongitude());
        }
    }
}
