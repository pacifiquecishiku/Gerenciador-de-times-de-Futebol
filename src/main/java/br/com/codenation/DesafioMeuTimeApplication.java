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
			for(Time time: listaTimes){
				if(time.getId()==idTime){
					int index=listaTimes.indexOf(time);
					listaTimes.get(index).getJogadores().add(jogador);
				}
			}
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

	@Desafio("buscarCapitaoDoTime")
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

	@Desafio("buscarJogadoresDoTime")
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

	@Desafio("buscarMelhorJogadorDoTime")
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
        Long idJogadorMaisVelho = null;
        LocalDate dataNascimento = LocalDate.now();
        if (!idTimes.contains(idTime)) {
            throw (new TimeNaoEncontradoException());
        } else {
            for (Time time : listaTimes) {
                if (time.getId() == idTime) {
                    int qtdJogadores = time.getJogadores().size();
                    for (int i=0;i<qtdJogadores;i++) {
                        if(dataNascimento.isAfter(time.getJogadores().get(i).getDataNascimento())){
                            dataNascimento=time.getJogadores().get(i).getDataNascimento();
                            idJogadorMaisVelho=time.getJogadores().get(i).getId();
                        }
                    }
                }
            }
        }
        return idJogadorMaisVelho;
    }

	@Desafio("buscarTimes")
	public List<Long> buscarTimes() {
		Collections.sort(idTimes);

		return idTimes;
	}

	@Desafio("buscarJogadorMaiorSalario")//FALHOU
	public Long buscarJogadorMaiorSalario(Long idTime) {
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
						}else if(salarioJogador.byteValue() == jogador.getSalario().byteValue()){
							if(idJogadorMaiorSalario>jogador.getId()){
								salarioJogador = jogador.getSalario();
								idJogadorMaiorSalario = jogador.getId();
							}
						}
					}
				}
			}

		}
		return idJogadorMaiorSalario;
	}

	@Desafio("buscarSalarioDoJogador")
	public BigDecimal buscarSalarioDoJogador(Long idJogador) {
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

	@Desafio("buscarTopJogadores") //CONTINUAR
	public List<Long> buscarTopJogadores(Integer top) {
		List<Long> topJogadores = new ArrayList<>();
        List<Long> topJogadoresFinal = new ArrayList<>();
		List<Integer> nivelJogadores = new ArrayList<>();
		if(listaJogadores.isEmpty()){
			return new ArrayList<>();
		}else {
			for (Jogador jogador : listaJogadores) {
				topJogadores.add(jogador.getId());
				nivelJogadores.add(jogador.getNivelHabilidade());
			}
			for (int i = topJogadores.size(); i >= 0; i--) {
				for (int j = 0; j < i; j++) {
					int maior = nivelJogadores.get(j);
					int menor = nivelJogadores.get(j + 1);
					if (menor > maior) {
						nivelJogadores.set(j, menor);
						nivelJogadores.set(j + 1, maior);

						Long idMaior = topJogadores.get(j);
						Long idMenor = topJogadores.get(j + 1);
						topJogadores.set(j, idMenor);
						topJogadores.set(j + 1, idMaior);
					}
				}
			}
			for (int i = 0; i < top; i++) {
				topJogadoresFinal.add(topJogadores.get(i));
			}
		}
		return topJogadoresFinal;
	}

	@Desafio("buscarCorCamisaTimeDeFora")//FALHOU
	public String buscarCorCamisaTimeDeFora(Long timeDaCasa, Long timeDeFora) {
		String corCamisa = "";
		if (!idTimes.contains(timeDaCasa) || !listaTimes.contains(timeDeFora)) {
			throw (new TimeNaoEncontradoException());
		}else {
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
		}
		return corCamisa;
	}
}
