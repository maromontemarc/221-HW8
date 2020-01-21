import java.util.ArrayList;

/**
 * DNAStrand.java:
 * This class represents a DNA strand
 *
 * @author Professor Rossi
 */
public class DNAStrand
{
    private String dna; //The dna strand represented as text
    private String header;

    /**
     * One argument constructor, initializes the class with the dna string
     *
     * @param dna The dna strand the class is representing
     */
    public DNAStrand( String dna )
    {
        this.dna = dna;
        this.scrubDNA();

        //Need to handle situation where a DNA strand does not evenly slice
        if( this.dna.length() % 3 != 0 )
        {
            if( this.dna.length() % 3 == 1 )
            {
                this.dna += "AA";
            }
            else if( this.dna.length() %3 == 2 )
            {
                this.dna += "A";
            }
        }
    }

    public void setHeader( String h )
    {
        header = h;
    }
    /**
     * Cleans up the DNA, removing X's and N's
     *
     * (Note: I forgot to mention the Ns in the last spec...if you left them in you will not loose points)
     */
    private void scrubDNA()
    {
        this.dna = this.dna.replaceAll( "X", "A" );
        this.dna = this.dna.replaceAll( "N", "A" );
    }

    /**
     * Finds the protein coding regions and prints them to the screen
     */
    public ArrayList<String> findPCRs()
    {
        boolean inSeq = false;
        ArrayList<String> proteinRegionList = new ArrayList<>();
        String proteinRegion = "";

        for( int i = 0; i < this.dna.length(); i += 3 )
        {
            char c1 = this.dna.charAt( i );
            char c2 = this.dna.charAt( i + 1 );
            char c3 = this.dna.charAt( i + 2 );

            String codon = new Character( c1 ).toString() + c2 + c3;

            if( codon.equals( "ATG" ) )
            {
                proteinRegion += codon;
                inSeq = true;
            }
            else if( inSeq && ( codon.equals( "TAA" ) || codon.equals( "TAG" ) || codon.equals( "TGA" ) ) )
            {
                proteinRegion += codon;
                proteinRegionList.add( proteinRegion );
                proteinRegion = "";
                inSeq = false;
            }
            else if( inSeq )
            {
                proteinRegion += codon;
            }
        }

        /*
        Need to shut a sequence if we finish the parse and we are still in a sequence.  Most likely biologists would
        not agree with just sticking a TGA on the end of an incomplete coding region, but I am going to do it anyway.
        */
        if( inSeq )
        {
            proteinRegion += "TGA";
            proteinRegionList.add( proteinRegion );
        }

        return proteinRegionList;
    }
}