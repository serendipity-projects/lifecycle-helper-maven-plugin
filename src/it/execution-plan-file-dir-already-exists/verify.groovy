#!/usr/bin/env groovy
File buildLog = new File(basedir, 'build.log')
File defatultOutputFile = new File(basedir, 'target/lifecycle-helper.CSV')
File outputFile = new File(basedir, 'outDir/file1.csv')

assert buildLog.exists()
assert !defatultOutputFile.exists()
assert outputFile.exists()
