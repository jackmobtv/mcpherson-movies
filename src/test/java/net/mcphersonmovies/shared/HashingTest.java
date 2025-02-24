package net.mcphersonmovies.shared;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

class HashingTest {

    @Test
    void hash() throws NoSuchAlgorithmException {
        assertEquals("9c9064c59f1ffa2e174ee754d2979be80dd30db552ec03e7e327e9b1a4bd594e", Hashing.hash("newuser"));
        assertEquals("fdf5a27d80d64e12cd1314a4a96fa90461c3d7014d90fe16b807df12fb9640d6", Hashing.hash("Newuser@123"));
    }
}