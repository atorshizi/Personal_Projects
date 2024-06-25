# Use Date.now() to Generate Reading IDs

## Context and Problem Statement

How do we make unique identifiers to reference tarot readings stored in Local Storage while preforming CRUD actions?

## Considered Options

* [Date.now()](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Date/now)
* [UUID Package](https://www.npmjs.com/package/uuid)
* [Crypto API](https://developer.mozilla.org/en-US/docs/Web/API/Crypto/randomUUID)

## Decision Outcome

Chosen option: "Date.now()" is best since it is standard to all browsers and doesn't rely on a dependency. There is a possibility for collisions if multiple creations/updates are preformed quickly in succession, but this is not an expected usage pattern so this option is sufficient.