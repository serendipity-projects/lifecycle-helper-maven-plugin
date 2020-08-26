# Usage


In order to use plugin prefix you must activate the plugin group in your Maven *settings.xml*:

```
<pluginGroups>
    <pluginGroup>it.serendigity.maven.plugins</pluginGroup>
</pluginGroups>
```

So you can execute the plugin with the command
```
> mvn lifecycle-helper:execution-plan
```

Otherwise you can execute the plugin with the command
```
> mvn it.serendigity.maven.plugins:lifecycle-helper-maven-plugin:execution-plan
```

## Brief examples on how to use the Plugin

### Display execution plan of a project

If you run execution-plan goal without arguments all maven lifecycle/phases are displayed in the execution plan order grouped by Lifecycle.

```
> mvn it.serendigity.maven.plugins:lifecycle-helper-maven-plugin:execution-plan
```

```
Project: Lifecycle Helper Maven Plugin (lifecycle-helper-maven-plugin:0.0.1-SNAPSHOT).
Execution plan for run phases: [post-clean, deploy, site-deploy]
Group by: PLAN_ORDER
Filter plugins:
--------------------------------------------------------------------------------------------------------------------------------------------------
 | PLAN ORDER | LIFECYCLE | LIFECYCLE PHASE        | PLUGIN ARTIFACT ID         | PLUGIN EXECUTION ID               | PLUGIN GOAL               |
--------------------------------------------------------------------------------------------------------------------------------------------------
 | 0          | clean     | clean                  | maven-clean-plugin         | default-clean                     | clean                     |
 | 1          | default   | validate               | maven-enforcer-plugin      | enforce-java-version              | display-info              |
 | 2          | default   | validate               | maven-enforcer-plugin      | enforce-java-version              | enforce                   |
 | 3          | default   | validate               | maven-enforcer-plugin      | enforce-maven                     | enforce                   |
 | 4          | default   | initialize             | jacoco-maven-plugin        | default                           | prepare-agent             |
 | 5          | default   | generate-sources       | maven-plugin-plugin        | help-goal                         | helpmojo                  |
 | 6          | default   | process-resources      | maven-resources-plugin     | default-resources                 | resources                 |
 | 7          | default   | compile                | maven-compiler-plugin      | default-compile                   | compile                   |
 | 8          | default   | process-classes        | maven-plugin-plugin        | default-descriptor                | descriptor                |
 | 9          | default   | process-classes        | maven-plugin-plugin        | mojo-descriptor                   | descriptor                |
 | 10         | default   | process-test-resources | maven-resources-plugin     | default-testResources             | testResources             |
 | 11         | default   | test-compile           | maven-compiler-plugin      | default-testCompile               | testCompile               |
 | 12         | default   | test                   | maven-surefire-plugin      | default-test                      | test                      |
 | 13         | default   | test                   | jacoco-maven-plugin        | report                            | report                    |
 | 14         | default   | package                | maven-jar-plugin           | default-jar                       | jar                       |
 | 15         | default   | package                | maven-plugin-plugin        | default-addPluginArtifactMetadata | addPluginArtifactMetadata |
 | 16         | default   | package                | maven-javadoc-plugin       | generate-standard-javadoc         | jar                       |
 | 17         | default   | package                | maven-source-plugin        | attach-sources                    | jar-no-fork               |
 | 18         | default   | package                | maven-site-plugin          | attach-descriptor                 | attach-descriptor         |
 | 19         | default   | pre-integration-test   | jacoco-maven-plugin        | default                           | prepare-agent-integration |
 | 20         | default   | verify                 | maven-gpg-plugin           | sign-artifacts                    | sign                      |
 | 21         | default   | install                | maven-install-plugin       | default-install                   | install                   |
 | 22         | default   | deploy                 | maven-deploy-plugin        | default-deploy                    | deploy                    |
 | 23         | default   | deploy                 | maven-deploy-plugin        | skip-default-deploy               | deploy                    |
 | 24         | default   | deploy                 | nexus-staging-maven-plugin | default-deploy                    | deploy                    |
 | 25         | site      | site                   | maven-site-plugin          | default-site                      | site                      |
 | 26         | site      | site-deploy            | maven-site-plugin          | default-deploy                    | deploy                    |
```

It is possible to indicate a specific lifecycle phase/s with parameter *lifecycle-helper.phases*.
For example if you want to list the execution plan only for *clean* and *compile* phases.

```
> mvn it.serendigity.maven.plugins:lifecycle-helper-maven-plugin:execution-plan -Dlifecycle-helper.phases=clean,compile
```

```
Project: Lifecycle Helper Maven Plugin (lifecycle-helper-maven-plugin:0.0.1-SNAPSHOT).
Execution plan for run phases: [clean, compile]
Group by: PLAN_ORDER
Filter plugins:
----------------------------------------------------------------------------------------------------------------
 | PLAN ORDER | LIFECYCLE | LIFECYCLE PHASE   | PLUGIN ARTIFACT ID     | PLUGIN EXECUTION ID  | PLUGIN GOAL   |
----------------------------------------------------------------------------------------------------------------
 | 0          | clean     | clean             | maven-clean-plugin     | default-clean        | clean         |
 | 1          | default   | validate          | maven-enforcer-plugin  | enforce-java-version | display-info  |
 | 2          | default   | validate          | maven-enforcer-plugin  | enforce-java-version | enforce       |
 | 3          | default   | validate          | maven-enforcer-plugin  | enforce-maven        | enforce       |
 | 4          | default   | initialize        | jacoco-maven-plugin    | default              | prepare-agent |
 | 5          | default   | generate-sources  | maven-plugin-plugin    | help-goal            | helpmojo      |
 | 6          | default   | process-resources | maven-resources-plugin | default-resources    | resources     |
 | 7          | default   | compile           | maven-compiler-plugin  | default-compile      | compile       |
```

### Group by execution plan result
You can order and group the execution plan result by any column of the table filling the parameter *lifecycle-helper.groupby*.
Allowed values are the values of *MavenExecutionAttribute* enum.

Following two examples with run phases parameter *compile*:
- group by PHASE
- group by PLUGIN

```
> mvn it.serendigity.maven.plugins:lifecycle-helper-maven-plugin:execution-plan -Dlifecycle-helper.groupby=PHASE -Dlifecycle-helper.phases=compile

Project: Lifecycle Helper Maven Plugin (lifecycle-helper-maven-plugin:0.0.1-SNAPSHOT).
Execution plan for run phases: [compile]
Group by: PHASE
Filter plugins:
--------------------------------------------------------------------------------------------
 | PLAN ORDER | LIFECYCLE | PLUGIN ARTIFACT ID     | PLUGIN EXECUTION ID  | PLUGIN GOAL   |
--------------------------------------------------------------------------------------------

Lifecycle phase: compile ....................................................................
 | 6          | default   | maven-compiler-plugin  | default-compile      | compile       |

Lifecycle phase: generate-sources ...........................................................
 | 4          | default   | maven-plugin-plugin    | help-goal            | helpmojo      |

Lifecycle phase: initialize .................................................................
 | 3          | default   | jacoco-maven-plugin    | default              | prepare-agent |

Lifecycle phase: process-resources ..........................................................
 | 5          | default   | maven-resources-plugin | default-resources    | resources     |

Lifecycle phase: validate ...................................................................
 | 0          | default   | maven-enforcer-plugin  | enforce-java-version | display-info  |
 | 1          | default   | maven-enforcer-plugin  | enforce-java-version | enforce       |
 | 2          | default   | maven-enforcer-plugin  | enforce-maven        | enforce       |                |
```

```
> mvn it.serendigity.maven.plugins:lifecycle-helper-maven-plugin:execution-plan -Dlifecycle-helper.groupby=PLUGIN -Dlifecycle-helper.phases=compile

Project: Lifecycle Helper Maven Plugin (lifecycle-helper-maven-plugin:0.0.1-SNAPSHOT).
Execution plan for run phases: [compile]
Group by: PLUGIN
Filter plugins:
---------------------------------------------------------------------------------------
 | PLAN ORDER | LIFECYCLE | LIFECYCLE PHASE   | PLUGIN EXECUTION ID  | PLUGIN GOAL   |
---------------------------------------------------------------------------------------

Plugin Artifact Id: jacoco-maven-plugin ................................................
 | 3          | default   | initialize        | default              | prepare-agent |

Plugin Artifact Id: maven-compiler-plugin ..............................................
 | 6          | default   | compile           | default-compile      | compile       |

Plugin Artifact Id: maven-enforcer-plugin ..............................................
 | 0          | default   | validate          | enforce-java-version | display-info  |
 | 1          | default   | validate          | enforce-java-version | enforce       |
 | 2          | default   | validate          | enforce-maven        | enforce       |

Plugin Artifact Id: maven-plugin-plugin ................................................
 | 4          | default   | generate-sources  | help-goal            | helpmojo      |

Plugin Artifact Id: maven-resources-plugin .............................................
 | 5          | default   | process-resources | default-resources    | resources     |

```

Moreover it's possible to filter the result to a specific plugin filling the parameter *lifecycle-helper.filter.plugins*.

```
mvn it.serendigity.maven.plugins:lifecycle-helper-maven-plugin:execution-plan -Dlifecycle-helper.filter.plugins=maven-site-plugin

Project: Lifecycle Helper Maven Plugin (lifecycle-helper-maven-plugin:0.0.1-SNAPSHOT).
Execution plan for run phases: [post-clean, deploy, site-deploy]
Group by: PLAN_ORDER
Filter plugins: [maven-site-plugin]
-------------------------------------------------------------------------------------------------------------
 | PLAN ORDER | LIFECYCLE | LIFECYCLE PHASE | PLUGIN ARTIFACT ID | PLUGIN EXECUTION ID | PLUGIN GOAL       |
-------------------------------------------------------------------------------------------------------------
 | 18         | default   | package         | maven-site-plugin  | attach-descriptor   | attach-descriptor |
 | 25         | site      | site            | maven-site-plugin  | default-site        | site              |
 | 26         | site      | site-deploy     | maven-site-plugin  | default-deploy      | deploy            |
 ```