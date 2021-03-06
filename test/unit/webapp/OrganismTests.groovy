package webapp



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Organism)
class OrganismTests {

    void testConstraints() {
        def validOrganism = new Organism(
                organismId: 1,
                scientificName: "Paineus in the asseus",
                gcPercentage: "42",
                rscuCodonDistribution: [ACT: "20"],
                mcufCodonDistribution: [ACT: "20"]
        )
        mockForConstraintsTests(Organism, [validOrganism])

        //make an empty organism, this should fail
        def organism = new Organism()
        assert !organism.validate()

        assert "nullable" == organism.errors["scientificName"]
        assert "nullable" == organism.errors["rscuCodonDistribution"]
        assert "nullable" == organism.errors["mcufCodonDistribution"]
        assert "nullable" == organism.errors["gcPercentage"]

        //test the unique constraint on organismId
        organism = new Organism(
                organismId: 1,
                scientificName: "homo sapien",
                gcPercentage: "42",
                rscuCodonDistribution: [ACT: "20"],
                mcufCodonDistribution: [ACT: "20"]
        )
        assert !organism.validate()
        assert "unique" == organism.errors["organismId"]

        //Make sure we can create a valid organism with all properties set
        organism = new Organism(
                organismId: 2,
                taxonomyId: 2,
                scientificName: "homo sapien",
                gcPercentage: "42",
                rscuCodonDistribution: [ACT: "20"],
                mcufCodonDistribution: [ACT: "20"]
        )
        assert organism.validate()
    }
}
