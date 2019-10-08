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
			BigDecimal salario) throws IdentificadorUtilizadoException, TimeNaoEncontradoException {
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
	public void definirCapitao(Long idJogador) throws JogadorNaoEncontradoException {
		if (!idJogadores.contains(idJogador)) {
			throw (new JogadorNaoEncontradoException());
		} else {
			for (Jogador jogador : listaJogadores) {
				if (jogador.getId() == idJogador) {
					jogador.setCapitao(true);
				} else if (jogador.isCapitao()) {
					jogador.setCapitao(false);
				}
			}
		}
	}

	@Desafio("buscarCapitaoDoTime")
	public Long buscarCapitaoDoTime(Long idTime) throws TimeNaoEncontradoException, CapitaoNaoInformadoException {
		Long idCapitao = null;
		if (!idTimes.contains(idTime)) {
			throw (new TimeNaoEncontradoException());
		} else {
			for (Time time : listaTimes) {
				if (time.getId() == idTime) {
					for (Jogador jogador : time.getJogadores()) {
						if (jogador.isCapitao()) {
							idCapitao = jogador.getId();
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
	public String buscarNomeJogador(Long idJogador) throws JogadorNaoEncontradoException {
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
	public String buscarNomeTime(Long idTime) throws TimeNaoEncontradoException {
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

	@Desafio("buscarJogadoresDoTime")
	public List<Long> buscarJogadoresDoTime(Long idTime) throws TimeNaoEncontradoException {
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

	@Desafio("buscarMelhorJogadorDoTime")
	public Long buscarMelhorJogadorDoTime(Long idTime) throws TimeNaoEncontradoException {
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
						} else if (jogador.getNivelHabilidade() == habilidade) {
							if(jogador.getId()<idMelhorJogador){
								idMelhorJogador = jogador.getId();
							}
						}
					}
				}
			}
		}

		return idMelhorJogador;
	}

	@Desafio("buscarJogadorMaisVelho")
	public Long buscarJogadorMaisVelho(Long idTime) throws TimeNaoEncontradoException {
		List<Long> idJogadoresTime = new ArrayList<>();
		Long idJogadorMaisVelho = null;
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
			Collections.sort(idJogadoresTime);
			idJogadorMaisVelho = idJogadoresTime.get(idJogadoresTime.size());
		}
		return idJogadorMaisVelho;
	}

	@Desafio("buscarTimes")
	public List<Long> buscarTimes() {
		Collections.sort(idTimes);

		return idTimes;
	}

	@Desafio("buscarJogadorMaiorSalario")
	public Long buscarJogadorMaiorSalario(Long idTime) throws TimeNaoEncontradoException {
		BigDecimal salarioJogador = new BigDecimal(0);
		Long idJogadorMaiorSalario = null;
		if (!idTimes.contains(idTime)) {
			throw new TimeNaoEncontradoException();
		} else {
			for (Time time : listaTimes) {
				if (time.getId() == idTime) {
					for (Jogador jogador : time.getJogadores()) {
						if (salarioJogador.byteValue() < jogador.getSalario().byteValue()) {
							salarioJogador = jogador.getSalario();
							idJogadorMaiorSalario = jogador.getId();
						}
					}
				}
			}

		}
		return idJogadorMaiorSalario;
	}

	@Desafio("buscarSalarioDoJogador")
	public BigDecimal buscarSalarioDoJogador(Long idJogador) throws JogadorNaoEncontradoException {
		BigDecimal salario = new BigDecimal(0);
		if (!idJogadores.contains(idJogador)) {
			throw new JogadorNaoEncontradoException();
		} else {
			for (Jogador jogador : listaJogadores) {
				if (jogador.getId() == idJogador) {
					salario = jogador.getSalario();
				}
			}
		}
		return salario;
	}

	@Desafio("buscarTopJogadores")
	public List<Long> buscarTopJogadores(Integer top) {
		List<Long> topJogadores = new ArrayList<>();
		List<Integer> niveis = new ArrayList<>();
		for(Jogador jogador: listaJogadores){
			niveis.add(jogador.getNivelHabilidade());
			topJogadores.add(jogador.getId());
		}

		return topJogadores;
	}

	@Desafio("buscarCorCamisaTimeDeFora")
	public String buscarCorCamisaTimeDeFora(Long timeDaCasa, Long timeDeFora) {
		String corCamisa = null;
		for (Time timeCasa : listaTimes) {
			if (timeCasa.getId() == timeDaCasa) {
				for (Time timeFora : listaTimes) {
					if (timeFora.getId() == timeDeFora) {
						if (timeCasa.getCorUniformePrincipal().equals(timeFora.getCorUniformePrincipal())) {
							corCamisa = timeFora.getCorUniformeSecundario();
						} else {
							corCamisa = timeFora.getCorUniformePrincipal();
						}
					}
				}
			}
		}
		return corCamisa;
	}

}
