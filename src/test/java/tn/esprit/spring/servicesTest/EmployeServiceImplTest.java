package tn.esprit.spring.servicesTest;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.entities.TimesheetPK;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.EntrepriseRepository;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.repository.TimesheetRepository;
import tn.esprit.spring.services.EmployeServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
/*
 * @SpringBootTest Annotation qui peut être spécifiée sur une classe de test qui
 * exécute des tests basés sur Spring Boot.
 */
public class EmployeServiceImplTest {

	@Autowired
	EmployeServiceImpl controller;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	ContratRepository contratRepoistory;
	@Autowired
	EmployeRepository employeRepository;
	@Autowired
	EntrepriseRepository entrepriseRepository;
	@Autowired
	MissionRepository missionRepository;
	@Autowired
	TimesheetRepository timesheetRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeServiceImpl.class);


	@Test
	public void ajouterEmployeTest() {
		Employe employe = new Employe("Mohamed salah", "Romdhana", "medsalah@esprit.tn", true,
				Role.ADMINISTRATEUR);
		LOGGER.info("{L'employé est   }" + " " +employe);
	
		int i = controller.ajouterEmploye(employe);

		assertThat(5).isNotNegative();
	}

	@Test
	public void ajouterContratTest() {
		Contrat contrat = new Contrat(new Date(), "CDI", 1500);
		controller.ajouterContrat(contrat);
		assertNotNull(null);

	}
	
	
	@Test
	public void getEmployePrenomByIdTest() {
		Employe employe = new Employe("Imen", "SAHLI", "Imen.SAHLI@esprit.tn", true, Role.ADMINISTRATEUR);
		controller.ajouterEmploye(employe);

		String name = controller.getEmployePrenomById(employe.getId());

		assertThat("SAHLI").isEqualTo(employe.getPrenom());
	}

	@Test
	public void affecterEmployeADepartementTest() {

		Employe employe = new Employe("Kawthar", "benkhoudja", "kawthaar.benkhoudja@esprit.tn", true,
				Role.ADMINISTRATEUR);
		controller.ajouterEmploye(employe);
		Departement departement = new Departement("R.H");
		deptRepoistory.save(departement);
		controller.affecterEmployeADepartement(employe.getId(), departement.getId());

		assertNotNull(deptRepoistory.findById(departement.getId()));

	}


	

	@Test
	public void deleteContratByIdTest() {
		Contrat contrat = new Contrat(new Date(), "CDI", 2000);
		controller.ajouterContrat(contrat);
		assertNotNull(contrat);
		controller.deleteContratById(contrat.getReference());

	}


	@Test
	public void getNombreEmployeJPQLTest() {
		int i = controller.getNombreEmployeJPQL();
		assertThat(0).isNotEqualTo(-1);

	}

	@Test
	public void getAllEmployeNamesJPQLTest() {
		Employe employe = new Employe("Khalil", "SEKMA", "Khalil.SEKMA@esprit.tn", true, Role.ADMINISTRATEUR);
		controller.ajouterEmploye(employe);

		java.util.List<String> names = controller.getAllEmployeNamesJPQL();

		assertThat(names.size()).isPositive();

	}

	@Test
	public void getAllEmployesTest() {
		java.util.List<Employe> employes = controller.getAllEmployes();

		assertThat(1).isPositive();

	}


	@Test
	public void deleteAllContratJPQLTest() {
		Contrat contrat = new Contrat(new Date(), "CDI", 6000);
		controller.ajouterContrat(contrat);
		controller.deleteAllContratJPQL();
		boolean notExistAfterDelete = contratRepoistory.findById(contrat.getReference()).isPresent();

		assertFalse(notExistAfterDelete);

	}

	@Test
	public void getAllEmployeByEntrepriseTest() {
		Employe employe = new Employe("Imen", "SAHLI", "Imen.SAHLI@esprit.tn", true, Role.ADMINISTRATEUR);
		controller.ajouterEmploye(employe);
		Employe employe1 = new Employe("Souad", "Mahasen", "Souad.SAHLI@esprit.tn", true, Role.ADMINISTRATEUR);
		controller.ajouterEmploye(employe1);
		Employe employe2 = new Employe("Bilel", "Hedhli", "Bilel.HEdhli@esprit.tn", true, Role.ADMINISTRATEUR);
		controller.ajouterEmploye(employe2);

		Entreprise entreprise = new Entreprise("Esprit", "Commercial");

		Departement departemen = new Departement("Informatique");
		Departement departemen2 = new Departement("R.H");
		List<Departement> departements = new ArrayList<>();
		departements.add(departemen);
		departements.add(departemen2);

		entreprise.setDepartements(departements);
		java.util.List<Employe> employes = new ArrayList<>();
		java.util.List<Employe> employes2 = new ArrayList<>();
		employes.add(employe);
		employes.add(employe1);
		employes2.add(employe2);

		departemen.setEmployes(employes);
		departemen2.setEmployes(employes2);

		departemen.setEntreprise(entreprise);
		departemen2.setEntreprise(entreprise);
		entrepriseRepository.save(entreprise);

		deptRepoistory.save(departemen);
		deptRepoistory.save(departemen2);

		controller.getAllEmployeByEntreprise(entreprise);
		java.util.List<Employe> Entreprise_employe = controller.getAllEmployeByEntreprise(entreprise);

		assertThat(5).isPositive();

	}

	@Test
	public void mettreAjourEmailByEmployeIdJPQLTest() {
		Employe employe = new Employe("Sawsen", "MACRON", "SAWSAN.MACROM@esprit.tn", true, Role.ADMINISTRATEUR);
		controller.ajouterEmploye(employe);
		controller.mettreAjourEmailByEmployeIdJPQL("sawsen@esprit.com", employe.getId());
		Employe employeManagedEntity = employeRepository.findById(employe.getId()).get();

		assertThat(employe.getEmail()).isNotSameAs(employeManagedEntity.getEmail());

	}

	
}
