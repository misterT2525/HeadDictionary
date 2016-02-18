#!/bin/bash

set -e -u

if [ "$TRAVIS_REPO_SLUG" == "misterT2525/HeadDictionary" ] && \
   [ "$TRAVIS_JDK_VERSION" == "oraclejdk8" ] && \
   [ "$TRAVIS_PULL_REQUEST" == "false" ] && \
   [ "$TRAVIS_BRANCH" == "master" ]; then
  echo "Publishing website..."

  cp target/HeadDictionary.jar website

  cd website
  git init

  git config user.email "travis@travis-ci.org"
  git config user.name "travis-ci"

  git add .
  git commit -m "Deploy to GitHub Pages"

  git push --force --quiet "https://${GH_TOKEN}@github.com/misterT2525/HeadDictionary" master:gh-pages > /dev/null 2>&1

  echo "Published website"
fi
