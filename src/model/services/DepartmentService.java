package model.services;

import java.util.ArrayList;
import java.util.List;

import model.entities.Department;

public class DepartmentService {

	public List<Department> findAll() {
		List<Department> list = new ArrayList<>();
		list.add(new Department(1, 
				"Desenvolvimento de Software", 
				"Responsável pelo desenvolvimento de software para os produtos da empresa, "
				+ "incluindo aplicativos web e móveis, sistemas internos e plataformas de "
				+ "cliente.", "Desenvolvimento e manutenção de aplicativos web e móveis.\r\n"
						+ "Implementação de novos recursos e melhorias nos produtos existentes.\r\n"
						+ "Colaboração com outros departamentos para integrar sistemas e funcionalidades.\r\n"
						+ "Garantir a segurança e a qualidade do código através de testes e revisões.", 16));
		
		list.add(new Department(2, 
				"Recursos Humanos", 
				"Responsável por recrutamento, seleção, treinamento, desenvolvimento "
				+ "e gerenciamento de pessoal dentro da organização.", 
				"Recrutamento e seleção de novos funcionários.\r\n"
				+ "Administração de benefícios e folha de pagamento.\r\n"
				+ "Desenvolvimento e implementação de políticas de recursos humanos.\r\n"
				+ "Gestão de desempenho e avaliações de funcionários.\r\n"
				+ "Promoção de um ambiente de trabalho saudável e inclusivo.\r\n",
				+ 9));
		
		list.add(new Department(3, 
				"Marketing", 
				"Responsável por promover a marca, os produtos e os serviços da empresa, "
				+ "além de atrair e reter clientes.", 
				"Desenvolvimento e implementação de estratégias de marketing.\r\n"
				+ "Gestão de campanhas de publicidade online e offline.\r\n"
				+ "Análise de mercado e pesquisa de consumidor.\r\n"
				+ "Criação de conteúdo para redes sociais, blogs e outros canais.\r\n"
				+ "Colaboração com equipes de vendas e desenvolvimento de produtos.", 
				+ 9));
		
		return list;
	}
}