# Mystic Orbs

A Java project implementing a system of magical and space orbs with ovens.

## Project Structure

- **Orbs** - Different orb types (MagicOrb, SpaceOrb)
- **Ovens** - Ovens for creating orbs (MagicOven, SpaceOven, InfinityMagicOven)
- **Factory** - OrbFactory for creating orbs
- **Storage** - Resource management

## Testing

This project can be tested using unit tests:

```bash
# Run all tests
java -cp junit.jar org.junit.runner.JUnitCore MysticOrbs.orb.*Test MysticOrbs.oven.*Test
```

All tests are located in the `test/` directory and cover main functionalities.

## Requirements

- Java 8+
- JUnit for testing

