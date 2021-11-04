package br.com.springboot.curso_jdev_treinamento.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.curso_jdev_treinamento.model.Usuario;
import br.com.springboot.curso_jdev_treinamento.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {

	@Autowired /* IC/CD/CDI -Injeção de dependencia */
	private UsuarioRepository usuarioRepository;

	//
	// gravar no banco
	@RequestMapping(value = "/olamundo/{nome}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String retornaOlaMundo(@PathVariable String nome) {

		Usuario usuario = new Usuario();
		usuario.setNome(nome);

		usuarioRepository.save(usuario);/* salva usuario no bd */

		return "Ola mundo" + nome;
	}

	// listar todos
	@GetMapping(value = "listatodos") /* primeiro metodo da API */
	@ResponseBody /* retorna os dados para o corpo da resposta */
	public ResponseEntity<List<Usuario>> listaUsuario() {
		List<Usuario> usuarios = usuarioRepository.findAll();/* execua a consulta no banco de dados */
		//
		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);/* retornaria a lista (JSON) */
	}

	// salvar
	@PostMapping(value = "salvar") /* mapea a url */
	@ResponseBody /* descrição da resposta */
	public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) {/* recebe os dados para salvar */

		Usuario user = usuarioRepository.save(usuario);

		return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);/* poderia ser OK */
	}

	// atualizar
	@PutMapping(value = "atualizar") /* mapeia a url */
	@ResponseBody /* descrição da resposta */
	public ResponseEntity<?> atualizar(@RequestBody Usuario usuario) {
		if (usuario.getId() == null)
			return new ResponseEntity<String>("Id não foi informado para atualização.", HttpStatus.OK);
		Usuario user = usuarioRepository.saveAndFlush(usuario);
		return new ResponseEntity<Usuario>(user, HttpStatus.OK);
	}

	/* ataualizar da internet */
//	@PutMapping(value = "atualizar")
//	public ResponseEntity<?> atualizaTeste(@RequestBody Usuario usuario) {
//		if (usuario.getId() == null)
//			return new ResponseEntity<String>("id não informado", HttpStatus.BAD_REQUEST);
//		// return new ResponseEntity<>(usuarioRepository.saveAndFlush(usuario),
//		// HttpStatus.OK);
//		Usuario user = usuarioRepository.saveAndFlush(usuario);
//		return new ResponseEntity<Usuario>(user, HttpStatus.OK);
//	}
	// fim

	// delete sugestivo
	@DeleteMapping(value = "delete") /* deletar registro */
	@ResponseBody /* descrição resposta */
	public ResponseEntity<String> delete(@RequestParam Long idUser) {

		usuarioRepository.deleteById(idUser);
		return new ResponseEntity<String>("id:" + " " + idUser + "" + " Usuário deletado com sucesso!", HttpStatus.OK);
	}

	// pesquisar por id
	@GetMapping(value = "buscaruserid")
	@ResponseBody /* descrição da resposta */
	public ResponseEntity<Usuario> buscaruserid(@RequestParam(name = "idUser") Long idUser) {

		Usuario usuario = usuarioRepository.findById(idUser).get();

		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}

	// busca por nome
	@GetMapping(value = "buscarPorNome")
	@ResponseBody /* Descrição da resposta */
	public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(name = "name") String name) {
		List<Usuario> usuario = usuarioRepository.buscaPorNome(name.trim().toUpperCase());
		return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.OK);
	}

}
