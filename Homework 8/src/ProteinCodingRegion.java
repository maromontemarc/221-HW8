import java.util.Iterator;

public class ProteinCodingRegion
{
    private String region;

    public ProteinCodingRegion( String r )
    {
        region = r;
    }

    public void displayMRNA()
    {
        if( region.charAt(0) == '>' )
        {
            System.out.println();
            System.out.println( region );
        }else
        {
            StringBuilder tempString = new StringBuilder( region );

            for( int i = 0; i < region.length(); i++ )
            {
                char temp = region.charAt( i );

                switch( temp )
                {
                    case 'A':
                        tempString.setCharAt( i, 'U' );
                        break;
                    case 'T':
                        tempString.setCharAt( i, 'A' );
                        break;
                    case 'C':
                        tempString.setCharAt( i, 'G' );
                        break;
                    case 'G':
                        tempString.setCharAt( i,  'C' );
                }
            }

            System.out.println( tempString );
        }
    }

    public void displayAminoAcidSequence()
    {
        CodonTable codonTable = new CodonTable();

        if (region.charAt(0) == '>')
        {
            System.out.println();
            System.out.println(region);
        } else
        {
            for( int i = 0; i < region.length(); i = i + 3 )
            {
                String codon = region.substring( i, i + 3 );
                String acid = codonTable.getAminoAcidSequence(codon);

                if (acid != null)
                {
                    System.out.print( acid );
                }
            }

            System.out.println();
        }
    }
}
