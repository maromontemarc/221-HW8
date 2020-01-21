import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Program8
{
    public static int displayMenu()
    {
        int menuChoice = 0;
        Scanner keyboard = new Scanner( System.in );
        boolean validChoice = false;

        System.out.println( "How would you like the output displayed? (Enter 1, 2, or 3) " );

        while( !validChoice )
        {
            System.out.println("1.DNA");
            System.out.println("2.mRNA");
            System.out.println("3.Amino Acid Sequence ");
            if (keyboard.hasNextInt() )
            {
                menuChoice = keyboard.nextInt();

                if( menuChoice == 1 || menuChoice == 2 || menuChoice == 3 )
                {
                    validChoice = true;
                }
                else
                {
                    System.out.println( "Please enter valid option." );
                    validChoice = false;
                }
            }else
            {
                System.out.println( "Please enter valid option." );
                validChoice = false;
                keyboard.next();
            }
        }

        return menuChoice;
    }

    public static void main(String[] args)
    {
        int menuChoice;
        String filePath;
        Scanner fileScan = null;

        Scanner keyboard = new Scanner( System.in );

        System.out.println( "Please enter the file path: " );
        filePath = keyboard.nextLine();

        File file = new File( filePath );
        // C:\Users\Marc\IdeaProjects\Homework 8\Resources\test_long.fasta.txt

        try
        {
            fileScan = new Scanner( file );
        }
        catch ( FileNotFoundException fnfe )
        {
            System.out.println( "The file could not be found, shutting down" );
            System.exit( 1 );
        }

        menuChoice = displayMenu();

        //This is just more memory efficient, if you just used a String don't worry that is acceptable too
        StringBuilder dna = new StringBuilder();
        ArrayList<String> proteinRegionList = new ArrayList<>();
        int count = 0;

        while( fileScan.hasNextLine() )
        {
            String line = fileScan.nextLine().trim();

            //If we encounter the >, then we are at a new strand
            if( line.length() > 0 && line.charAt( 0 ) == '>' )
            {
                DNAStrand strand = new DNAStrand( dna.toString() );
                if( !(count == 0) )
                {
                    proteinRegionList.addAll( strand.findPCRs() );
                }
                proteinRegionList.add( line );
                count++;
                dna = new StringBuilder();
            }
            else if( line.length() == 1 )
            {
                //do nothing
            }
            else
            {
                dna.append( line );
            }
        }

        //There will be one last DNA strand that is not printed out, this will print it out
        DNAStrand strand = new DNAStrand( dna.toString() );
        proteinRegionList.addAll( strand.findPCRs() );

        Iterator<String> itr = proteinRegionList.iterator();

        switch( menuChoice )
        {
            // DNA
            case 1:
                while( itr.hasNext() )
                {
                    String temp = itr.next();

                    if( temp.charAt( 0 ) == '>' )
                    {
                        System.out.println();
                    }
                    System.out.println( temp );
                }
                break;
            // mRNA
            case 2:
                while( itr.hasNext() )
                {
                    ProteinCodingRegion codeRegion = new ProteinCodingRegion( itr.next() );
                    codeRegion.displayMRNA();
                }
                break;
            // Amino Acid Sequence;
            case 3:
                while( itr.hasNext() )
                {
                    ProteinCodingRegion codeRegion = new ProteinCodingRegion( itr.next() );
                    codeRegion.displayAminoAcidSequence();
                }
                break;
        }

    }
}
