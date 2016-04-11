# math-ops

A [re-frame](https://github.com/Day8/re-frame) game
to learn basic math operations while having fun.

## Development Mode

### Run application:

```
lein clean
lein figwheel dev
```

Figwheel will automatically push cljs changes to the browser.

Wait a bit, then browse to [http://localhost:3449](http://localhost:3449).

## Production Build

```
lein clean
lein cljsbuild once min
```

## TODO

- Make it work offline

- Represent numbers as dots (images or SVG) for initial level (Spike!)

- Store state between uses in local (local storage, cookies, etc)

- Scores

- Make it pretty

- React Native version

- Users

- Store statistics of successes and failures by level, operation and user

- Create custom levels

- Make competitions in which all users guess the same sequence of operations
(same config and same random sequence)