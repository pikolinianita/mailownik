/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.luccasso.mailownik.persistence;

import java.nio.file.Path;
import static org.assertj.core.api.Assertions.*;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 *
 * @author piko
 */
@ExtendWith(SoftAssertionsExtension.class)
public class DiscSaverTest {

    DiscSaver ds;

    @Test
    public void testMakeDirsIfNotExist(SoftAssertions softly) {
        ds = new DiscSaver(new DataBase());

        ds.dirPath = Path.of("e:/asiowytest/junit/test/method");
        softly.assertThat(ds.dirPath.toFile().exists()).as("Dir not Exist").isFalse();
        ds.prepareDirectory();
        softly.assertThat(ds.dirPath.toFile().exists()).as("Dir should Exist").isTrue();
        softly.assertThat(ds.dirPath.endsWith("method")).as("path not shorten").isTrue();

        //Tear Down
        var file = ds.dirPath.toFile();
        file.delete();
        file.getParentFile().delete();
        file.getParentFile().delete();
    }

    @Test
    public void testMakeDirsIfFileInputed() {
        ds = new DiscSaver(new DataBase());

        ds.dirPath = Path.of("testfiles/UnitTestConfig.txt");
        ds.prepareDirectory();
        
        assertThat(ds.dirPath.endsWith("testfiles")).as("Test with File").isTrue();

    }

    @Test
    public void testExistingDirectory() {
        ds = new DiscSaver(new DataBase());

        ds.dirPath = Path.of("e:/asiowytest");
        ds.prepareDirectory();
        
        assertThat(ds.dirPath.endsWith("asiowytest")).as("Test exaact Dir").isTrue();
    }
}
