//------------------------------------------------------------------------------------------------//
//                                                                                                //
//                                          C l i T e s t                                         //
//                                                                                                //
//------------------------------------------------------------------------------------------------//
// <editor-fold defaultstate="collapsed" desc="hdr">
//  Copyright © Hervé Bitteur and others 2000-2016. All rights reserved.
//  This software is released under the GNU General Public License.
//  Goto http://kenai.com/projects/audiveris to report bugs or suggestions.
//------------------------------------------------------------------------------------------------//
// </editor-fold>
package omr;

import omr.step.Step;

import omr.util.Dumping;

import junit.framework.TestCase;

import org.junit.Test;

import org.kohsuke.args4j.CmdLineException;

import java.util.Arrays;

/**
 * Unitary tests for CLI.
 *
 * @author Hervé Bitteur
 */
public class CLITest
        extends TestCase
{
    //~ Instance fields ----------------------------------------------------------------------------

    private final CLI instance = new CLI("AudiverisTest");

    //~ Constructors -------------------------------------------------------------------------------
    /**
     * Creates a new {@code CLITest} object.
     */
    public CLITest ()
    {
    }

    //~ Methods ------------------------------------------------------------------------------------
    @Test
    public void testOption ()
            throws Exception
    {
        System.out.println("\n+++ testOption");

        String[] args = new String[]{
            "-option", "omr.toto  :  totoValue", "-option",
            "omr.ui.tata=tataValue", "-option", "keyWithNoValue", "-option",
            "myKey : my value"
        };
        CLI.Parameters params = instance.getParameters(args);
        new Dumping().dump(params);
    }

    @Test
    public void testPrintCommandLine ()
    {
        System.out.println("\n+++ printCommandLine");
        instance.printCommandLine();
    }

    @Test
    public void testPrintUsage ()
    {
        System.out.println("\n+++ printUsage");
        instance.printUsage();
    }

    @Test
    public void testSheets ()
            throws Exception
    {
        System.out.println("\n+++ testSheets");

        String[] args = new String[]{"-sheets", "3", "4", "6"};
        CLI.Parameters params = instance.getParameters(args);
        new Dumping().dump(params);
        assertEquals(Arrays.asList(3, 4, 6).toString(), params.getSheetIds().toString());
    }

    @Test
    public void testSome ()
            throws Exception
    {
        System.out.println("\n+++ testSome");

        String[] args = new String[]{
            "-help", "-batch", "-sheets", "5 2", " 3", "-step", "PAGE",
            "myScript.xml", "my Input.pdf"
        };
        CLI.Parameters params = instance.getParameters(args);
        new Dumping().dump(params);
        assertEquals(true, params.batchMode);
        assertEquals(true, params.helpMode);
        assertEquals(Arrays.asList(2, 3, 5).toString(), params.getSheetIds().toString());
        assertEquals(Step.PAGE, params.step);
        assertEquals(2, params.arguments.size());
        assertEquals("myScript.xml", params.arguments.get(0).toString());
        assertEquals("my Input.pdf", params.arguments.get(1).toString());
    }

    @Test
    public void testStep ()
            throws Exception
    {
        System.out.println("\n+++ testStep");

        String[] args = new String[]{"-step", "PAGE"};
        CLI.Parameters params = instance.getParameters(args);
        new Dumping().dump(params);
        assertEquals(Step.PAGE, params.step);
    }

    @Test
    public void testStepEmpty ()
            throws Exception
    {
        System.out.println("\n+++ testStepEmpty");

        String[] args = new String[]{"-step"};

        try {
            CLI.Parameters params = instance.getParameters(args);

            fail();
        } catch (CmdLineException ex) {
            System.out.println(ex.getMessage());
            System.out.println(ex.getLocalizedMessage());
            assertTrue(ex.getMessage().contains("-step"));
        }
    }

    @Test
    public void testVoid ()
            throws Exception
    {
        System.out.println("\n+++ testVoid");

        String[] args = new String[0];
        CLI.Parameters params = instance.getParameters(args);
        new Dumping().dump(params);
    }
}