package br.com.codenation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.codenation.desafio.annotation.Desafio;
import br.com.codenation.desafio.app.MeuTimeInterface;
import br.com.codenation.desafio.exceptions.CapitaoNaoInformadoException;
import br.com.codenation.desafio.exceptions.IdentificadorUtilizadoException;
import br.com.codenation.desafio.exceptions.JogadorNaoEncontradoException;
import br.com.codenation.desafio.exceptions.TimeNaoEncontradoException;

public class DesafioMeuTimeApplication implements MeuTimeInterface {
	private List<Time> listaTimes = new ArrayList<>();
	private List<Jogador> listaJogadores = new ArrayList<>();
	private List<Long> idTimes = new ArrayList<>();
	private List<Long> idJogadores = new ArrayList<>();

	@Desafio("incluirTime")
	public void incluirTime(Long id, String nome, LocalDate dataCriacao, String corUniformePrincipal,
							String corUniformeSecundario) throws IdentificadorUtilizadoException {
		if (idTimes.contains(id)) {
			throw (new IdentificadorUtilizadoException());
		} else {
			Time novoTime = new Time(id, nome, dataCriacao, corUniformePrincipal, corUniformeSecundario);
			listaTimes.add(novoTime);
			idTimes.add(id);
		}
	}

	@Desafio("incluirJogador")
	public void incluirJogador(Long id, Long idTime, String nome, LocalDate dataNascimento, Integer nivelHabilidade,
							   BigDecimal salario) {
		if (idJogadores.contains(id)) {
			throw (new IdentificadorUtilizadoException());
		} else if (!idTimes.contains(idTime)) {
			throw (new TimeNaoEncontradoException());
		} else {
			Jogador jogador = new Jogador(id, idTime, nome, dataNascimento, nivelHabilidade, salario);
			listaJogadores.add(jogador);
			idJogadores.add(id);
		}
	}

	@Desafio("definirCapitao")
	public void definirCapitao(Long idJogador) {
		if (!idJogadores.contains(idJogador)) {
			throw (new JogadorNaoEncontradoException());
		} else {
			for(Time time : listaTimes){
				for(Jogador jogador : time.getJogadores()){
					int index = time.getJogadores().indexOf(jogador);
					if(jogador.getId()==idJogador){
						time.getJogadores().get(index).setCapitao(true);
					}else{
						time.getJogadores().get(index).setCapitao(false);
					}
				}
			}
		}
	}

	@Desafio("buscarCapitaoDoTime") //FALHOU
	public Long buscarCapitaoDoTime(Long idTime) {
		Long idCapitao = null;
		if (!idTimes.contains(idTime)) {
			throw (new TimeNaoEncontradoException());
		} else {
			for (Time time : listaTimes) {
				if (time.getId() == idTime) {
					for (Jogador jogador : time.getJogadores()) {
						if (jogador.isCapitao()) {
							idCapitao = jogador.getId();
							break;
						}
					}
				}
			}
		}
		if (idCapitao == null) {
			throw (new CapitaoNaoInformadoException());
		}
		return idCapitao;
	}

	@Desafio("buscarNomeJogador")
	public String buscarNomeJogador(Long idJogador) {
		String nomeJogador = null;
		if (!idJogadores.contains(idJogador)) {
			throw (new JogadorNaoEncontradoException());
		} else {
			for (Jogador jogador : listaJogadores) {
				if (jogador.getId() == idJogador) {
					nomeJogador = jogador.getNome();
				}
			}
		}
		return nomeJogador;
	}

	@Desafio("buscarNomeTime")
	public String buscarNomeTime(Long idTime) {
		String nomeTime = null;
		if (!idTimes.contains(idTime)) {
			throw (new TimeNaoEncontradoException());
		} else {
			for (Time time : listaTimes) {
				if (time.getId() == idTime) {
					nomeTime = time.getNome();
				}
			}
		}
		return nomeTime;
	}

	@Desafio("buscarJogadoresDoTime") //FALHOU
	public List<Long> buscarJogadoresDoTime(Long idTime) {
		List<Long> idJogadoresTime = new ArrayList<>();
		if (!idTimes.contains(idTime)) {
			throw new TimeNaoEncontradoException();
		} else {
			for (Time time : listaTimes) {
				if (time.getId() == idTime) {
					for (Jogador jogador : time.getJogadores()) {
						idJogadoresTime.add(jogador.getId());
					}
				}
			}
		}
		Collections.sort(idJogadoresTime);
		return idJogadoresTime;
	}

	@Desafio("buscarMelhorJogadorDoTime")//FALHOU
	public Long buscarMelhorJogadorDoTime(Long idTime) {
		Long idMelhorJogador = null;
		int habilidade = 0;
		if (!idTimes.contains(idTime)) {
			throw new TimeNaoEncontradoException();
		} else {
			for (Time time : listaTimes) {
				if (time.getId() == idTime) {
					for (Jogador jogador : time.getJogadores()) {
						if (jogador.getNivelHabilidade() > habilidade) {
							habilidade = jogador.getNivelHabilidade();
							idMelhorJogador = jogador.getId();
						}
					}
				}
			}
		}

		return idMelhorJogador;
	}

	@Desafio("buscarJogadorMaisVelho")
	public Long buscarJogadorMaisVelho(Long idTime) {
		throw new UnsupportedOperationException();
	}

	@Desafio("buscarTimes")
	public List<Long> buscarTimes() {
		throw new UnsupportedOperationException();
	}

	@Desafio("buscarJogadorMaiorSalario")
	public Long buscarJogadorMaiorSalario(Long idTime) {
		throw new UnsupportedOperationException();
	}

	@Desafio("buscarSalarioDoJogador")
	public BigDecimal buscarSalarioDoJogador(Long idJogador) {
		throw new UnsupportedOperationException();
	}

	@Desafio("buscarTopJogadores")
	public List<Long> buscarTopJogadores(Integer top) {
		throw new UnsupportedOperationException();
	}

	@Desafio("buscarCorCamisaTimeDeFora")
	public String buscarCorCamisaTimeDeFora(Long timeDaCasa, Long timeDeFora) {
		throw new UnsupportedOperationException();
	}

}
