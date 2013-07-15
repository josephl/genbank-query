import webapp.GCPercentage
import webapp.MeanCodonUsage
import webapp.Organism
import webapp.RSCU

class BootStrap  {
    def init = { servletContext ->

        println "Application starting up... "

        int ORGS = 200   // number of organisms to create for testing

        // check whether the test data already exists
        if (!Organism.count()) {

            Random random = new Random()
            def nucleotides = ['t', 'c', 'a', 'g'] as char[]
            Map aaDistribution  // distribution of codon usage in the sequence
            String key  // key codon
            double fraction    // used for codon fraction assignment


            // create test data
            for (int i = 1; i <= ORGS; i++)
            {
                // add new test organism
                new Organism( organismId: i, scientificName: "Organismus Numberus " + i.toString(),
                        taxonomyId: random.nextInt(1340000) + 1000 ).save(failOnError: true)

                // reset codon usage distribution
                aaDistribution = [:]


                // create and assign test codon usage data
                // for all possible keys
                for (int i1 = 0; i1 < 4;  i1++)
                {
                    for (int i2 = 0; i2 < 4;  i2++)  {

                        for (int i3 = 0; i3 < 4;  i3++){

                            // generate key codon
                            char c1 = nucleotides[i1]
                            char c2 = nucleotides[i2]
                            char c3 = nucleotides[i3]
                            key = c1.toString() + c2.toString() + c3.toString()

                            // generate random (small) fraction <= 50%
                            fraction = (random.nextInt(5000) + 1) / 10000

                            // assign fraction value to the key
                            aaDistribution.put(key, fraction)

                        }
                    }
                }

                // add mean codon usage obj for the organism
                new MeanCodonUsage( organismId: i, distribution: aaDistribution ).save(failOnError: true)

                // add GC%
                new GCPercentage( organismId: i, gcPercentage: random.nextFloat() * 20 ).save(failOnError: true)

				// (JGM) add RSCU obj, though this is not accurate RSCU distribution data
				new RSCU( organismId: i, distribution: aaDistribution ).save(failOnError: true)
            }
        }
    }
    def destroy = {
        println "Application shutting down... "
    }
}
