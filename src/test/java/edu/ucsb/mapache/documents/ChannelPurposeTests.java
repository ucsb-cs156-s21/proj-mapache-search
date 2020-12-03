package edu.ucsb.mapache.documents;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;

import net.codebox.javabeantester.JavaBeanTester;

public class ChannelPurposeTests {
    @Test
    public void test_toString() {
        ChannelPurpose cp = new ChannelPurpose("sampleValue", "sampleCreator");
        String expected = "{ value='sampleValue', creator='sampleCreator'}";
        assertEquals(expected, cp.toString());
    }

    @Test
    public void test_getAndSetValue() {
        ChannelPurpose cp = new ChannelPurpose();
        cp.setValue("sampleValue");
        assertEquals("sampleValue", cp.getValue());
    }

    @Test
    public void test_getAndSetCreator() {
        ChannelPurpose cp = new ChannelPurpose();
        cp.setCreator("sampleCreator");
        assertEquals("sampleCreator", cp.getCreator());
    }

    @Test
    public void test_hashCode_matchesOnSameContent() {
        ChannelPurpose cp1 = new ChannelPurpose("sampleValue", "sampleCreator");
        ChannelPurpose cp2 = new ChannelPurpose("sampleValue", "sampleCreator");
        assertEquals(cp1.hashCode(), cp2.hashCode());
    }

    @Test
    public void test_notEqualNull() {
        assertNotEquals(new ChannelPurpose(), null);
    }

    @Test
    public void test_notEqualDifferentClass() {
        ChannelPurpose cp1 = new ChannelPurpose("sampleValue", "sampleCreator");
        Assertions.assertFalse(cp1.equals(new Object()));
    }

    @Test
    public void testPurpose_equalsCopy() {
        ChannelPurpose cp1 = new ChannelPurpose("sampleValue", "sampleCreator");
        ChannelPurpose cp2 = new ChannelPurpose("sampleValue", "sampleCreator");
        assertTrue(cp1.equals(cp2));
    }

    @Test
    public void testPurpose_equalsSelf() {
        ChannelPurpose cp1 = new ChannelPurpose("sampleValue", "sampleCreator");
        assertTrue(cp1.equals(cp1));
    }



}

