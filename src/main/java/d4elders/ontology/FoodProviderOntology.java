package d4elders.ontology;

import org.mindswap.pellet.jena.PelletReasonerFactory;

import com.hp.hpl.jena.ontology.OntDocumentManager;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.util.FileManager;

import de.fuberlin.wiwiss.d2rq.jena.ModelD2RQ;

public class FoodProviderOntology {

	public static final String PREFIX = "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
			+ "PREFIX iswc: <http://annotation.semanticweb.org/iswc/iswc.daml#>"
			+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>" + "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>"
			+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
			+ "PREFIX vcard: <http://www.w3.org/2001/vcard-rdf/3.0#>"
			+ "PREFIX foodprovider: <http://www.semanticweb.org/linabarakat/ontologies/2014/8/foodprovider#>"
			+ "PREFIX nutritionmonitoring: <http://www.semanticweb.org/dorin/ontologies/nutritionmonitoring#>"
			+ "PREFIX nutritionassesment: <http://www.semanticweb.org/dorin/ontologies/nutritionassesment#>"
			+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
			+ "PREFIX vocab: <http://localhost:8080/resource/vocab/>" + "PREFIX dcterms: <http://purl.org/dc/terms/>"
			+ "PREFIX map: <file:/Users/richard/D2RQ/workspace/D2R" + "Q/doc/example/mapping-iswc.n3#>"
			+ "PREFIX foaf: <http://xmlns.com/foaf/0.1/>" + "PREFIX dc: <http://purl.org/dc/elements/1.1/>";

	public static final String FOODPROVIDER_OWL = "ontology3/foodprovider.owl";
	public static final String ONT_POLICY_RDF = "ontology3/ontology-policy.rdf";
	public static final String FOODPROVIDER_TTL = "ontology3/foodprovider2.ttl";

	private Model d2rData;
	private OntModel ontModel;
	private Model model;

	public FoodProviderOntology() {
		model = FileManager.get().loadModel(FOODPROVIDER_TTL);

		// Read input
		d2rData = new ModelD2RQ(model, null);
		Model ontData = getOntologyModel();
		Reasoner reasoner = PelletReasonerFactory.theInstance().create();
		InfModel infModel = ModelFactory.createInfModel(reasoner, ontData);
		OntModelSpec spec = new OntModelSpec(OntModelSpec.OWL_MEM);

		ontModel = ModelFactory.createOntologyModel(spec, infModel);

		ontModel.add(d2rData);
	}

	public Model getOntologyModel() {
		OntDocumentManager dm = new OntDocumentManager(ONT_POLICY_RDF);
		OntModelSpec modelSpec = PelletReasonerFactory.THE_SPEC;
		modelSpec.setDocumentManager(dm);
		OntModel ontModelWithImport = ModelFactory.createOntologyModel(modelSpec);
		Model model = ontModelWithImport.read(FileManager.get().open(FOODPROVIDER_OWL), "");
		return model;
	}

	public void queryModel(String q, Model model, Model data) {
		// Add d2r data
		model.add(data);

		Query query = QueryFactory.create(q);
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		ResultSet rs = qexec.execSelect();

		// while (rs.hasNext()) {
		// QuerySolution row = rs.nextSolution();
		// if (row.getResource("recipe") != null) {
		// //System.out.print(row.getLiteral("recipe") + "   ");
		// System.out.println(row.getLiteral("id"));
		// System.out.println(row.getLiteral("name"));
		// }
		// }

		ResultSetFormatter.out(rs, query);

		qexec.close();
	}

	public ResultSet queryModelForResult(String q, Model model, Model data) {
		// Add d2r data
		model.add(data);

		Query query = QueryFactory.create(q);
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		ResultSet rs = qexec.execSelect();
		// while (rs.hasNext()) {
		// QuerySolution row = rs.nextSolution();
		// if (row.getResource("person") != null)
		// System.out.println(row.getLiteral("first_name"));
		// }

		// while (rs.hasNext()) {
		// QuerySolution row = rs.nextSolution();
		// if (row.getResource("recipe") != null) {
		// // System.out.print(row.getLiteral("recipe") + "   ");
		// System.out.println(row.getLiteral("name"));
		// System.out.println("P: " + row.getLiteral("p"));
		// System.out.println("F: " + row.get("f"));
		// }
		// }

		// This has been commented - Should see if it affects memory by not closing it!
		// qexec.close();
		return rs;
	}

	public Model getD2rData() {
		return d2rData;
	}

	public void setD2rData(Model d2rData) {
		this.d2rData = d2rData;
	}

	public OntModel getOntModel() {
		return ontModel;
	}

	public void setOntModel(OntModel ontModel) {
		this.ontModel = ontModel;
	}

	public Model getModel() {
		return model;
	}
}
