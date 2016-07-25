# math-ops

A [re-frame](https://github.com/Day8/re-frame) game
to learn basic math operations while having fun.

## Live version

https://fgui.github.io/math-ops/

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

## Run tests

```
lein doo node unit-tests
```
[Running cljs.test tests on node.js using doo and karma](http://garajeando.blogspot.com.es/2016/04/running-cljstest-tests-on-nodejs-using.html)

## TODO

- Clean handlers

## Wishlist

- Scores

- Make it pretty

- Make it work offline

- Represent numbers as dots (images or SVG) for initial level (Spike!)

- React Native version

- Users

- Store statistics of successes and failures by level, operation and user

- Create custom levels

- Make competitions in which all users guess the same sequence of operations
(same config and same random sequence)
