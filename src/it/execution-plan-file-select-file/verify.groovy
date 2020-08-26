#!/usr/bin/env groovy
File buildLog = new File(basedir, 'build.log')
File outputFile = new File(basedir, 'target/otherNameOutputfile.CSV')

assert buildLog.exists()
assert outputFile.exists()
