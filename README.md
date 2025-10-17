# Spring Web Future Exception Handling

The following demsontrates handling runtime exceptions within a completeable future.

**Note:** Before running any of the examples below, make sure the server is running i.e. `mvn spring-boot:run`.

## Examples

Examples of hitting the `POST /api/v1/search` endpoint in JS and Bash can be found below.

### Java

Here is the Java example: `src/integration/test/java/com/example/web/api/v1/greeting/GreetingControllerTest.java`.

### JavaScript

In a browser tab, on the `http://localhost:8080`, you can run the followin gin the developer tools console:

```js
(async () => {
    const res = await fetch('http://localhost:8080/api/v1/search', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ term: 'foo' })
    })
    const data = await res.json()
    console.log('Data:', JSON.stringify(data))
})()
```

Error:

```js
(async () => {
    const res = await fetch('http://localhost:8080/api/v1/search', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ term: 'error' })
    })
    console.log(res.status === 204) // NO CONTENT
})()
```

### Bash

Alternatively, you can cURL:

```sh
# [200 OK] {"query":{"term":"foo"},"results":[]}
curl -X POST http://localhost:8080/api/v1/search \
  -H 'Content-Type: application/json' \
  -d '{"term":"foo"}'
```

```sh
# [204 NO CONTENT] (error is handled)
curl -X POST http://localhost:8080/api/v1/search \
  -H 'Content-Type: application/json' \
  -d '{"term":"error"}'
```
