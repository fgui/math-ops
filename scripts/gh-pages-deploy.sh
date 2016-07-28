#!/bin/sh
lein clean
lein cljsbuild once min
git add resources/public
git commit -m 'new deployment'
git subtree push --prefix resources/public origin gh-pages
