package com.diogopires.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.diogopires.cursomc.domain.Categoria;
import com.diogopires.cursomc.domain.Cidade;
import com.diogopires.cursomc.domain.Cliente;
import com.diogopires.cursomc.domain.Endereco;
import com.diogopires.cursomc.domain.Estado;
import com.diogopires.cursomc.domain.ItemPedido;
import com.diogopires.cursomc.domain.Pagamento;
import com.diogopires.cursomc.domain.PagamentoComBoleto;
import com.diogopires.cursomc.domain.PagamentoComCartao;
import com.diogopires.cursomc.domain.Pedido;
import com.diogopires.cursomc.domain.Produto;
import com.diogopires.cursomc.domain.enums.EstadoPagamento;
import com.diogopires.cursomc.domain.enums.TipoCliente;
import com.diogopires.cursomc.repositories.CategoriaRepository;
import com.diogopires.cursomc.repositories.CidadeRepository;
import com.diogopires.cursomc.repositories.ClienteRepository;
import com.diogopires.cursomc.repositories.EnderecoRepository;
import com.diogopires.cursomc.repositories.EstadoRepository;
import com.diogopires.cursomc.repositories.ItemPedidoRepository;
import com.diogopires.cursomc.repositories.PagamentoRepository;
import com.diogopires.cursomc.repositories.PedidoRepository;
import com.diogopires.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoriarepository;
	@Autowired
	private ProdutoRepository produtorepository;
	@Autowired
	private CidadeRepository cidaderepository;
	@Autowired
	private EstadoRepository estadorepository;
	@Autowired
	private ClienteRepository clienterepository;
	@Autowired
	private EnderecoRepository enderecorepository;
	@Autowired
	private PedidoRepository pedidorepository;
	@Autowired
	private PagamentoRepository pagamentorepository;
	@Autowired
	private ItemPedidoRepository itemPedidorepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}
	
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria (null,"Informática");
		Categoria cat2 = new Categoria (null,"Escritório");
		
		Produto p1 = new Produto(null,"Computador",2000.00);
		Produto p2 = new Produto(null,"Impressora",800.00);
		Produto p3 = new Produto(null,"Mouse",80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		
		categoriarepository.saveAll(Arrays.asList(cat1,cat2));
		produtorepository.saveAll(Arrays.asList(p1,p2,p3));
		
		Estado est1 = new Estado(null,"Minas Gerais");
		Estado est2 = new Estado(null,"São Paulo");
		
		Cidade c1 = new Cidade(null,"Uberlândia",est1);
		Cidade c2 = new Cidade(null,"São Paulo",est2);
		Cidade c3 = new Cidade(null,"Campinas",est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		estadorepository.saveAll(Arrays.asList(est1,est2));
		cidaderepository.saveAll(Arrays.asList(c1,c2,c3));
		
		Cliente cli1 = new Cliente(null,"Maria Silva","maria@gmail.com","36378912377",TipoCliente.PESSOAFISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("27363323","93838393"));
		
		Endereco e1 = new Endereco(null,"Rua Flores","300","Apto 303", "Jardim","38220834",cli1,c1);
		Endereco e2 = new Endereco(null,"Avenida Matos","105","Sala 800", "Centro","38777012",cli1,c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		
		clienterepository.saveAll(Arrays.asList(cli1));
		enderecorepository.saveAll(Arrays.asList(e1,e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido pedi1 = new Pedido(null, sdf.parse("30/09/2017 10:32"),cli1,e1 );
		Pedido pedi2 = new Pedido(null, sdf.parse("10/10/2017 19:35"),cli1,e2 );
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, pedi1, 6);
		pedi1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, pedi2, sdf.parse("20/10/2017 00:00"), null);
		pedi2.setPagamento(pagto2);
		
		cli1.getPedido().addAll(Arrays.asList(pedi1,pedi2));
		
		pedidorepository.saveAll(Arrays.asList(pedi1,pedi2));
		pagamentorepository.saveAll(Arrays.asList(pagto1,pagto2));
		
		ItemPedido ip1 = new ItemPedido(pedi1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(pedi1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(pedi2, p2, 100.00, 1, 800.00);
		
		pedi1.getItens().addAll(Arrays.asList(ip1,ip2));
		pedi2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidorepository.saveAll(Arrays.asList(ip1,ip2,ip3));
		
	}

}
