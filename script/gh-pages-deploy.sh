#!/bin/sh
lein with-profile deployment clean
lein cljsbuild once min
cp resources/public/index.html resources/deploy/index.html
git add resources/deploy
git commit -m 'new deployment'
git subtree push --prefix resources/deploy origin gh-pages
