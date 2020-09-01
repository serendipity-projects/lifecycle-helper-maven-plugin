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
Note: For all the examples we will use the pom from the integration test sample project
[View pom](https://github.com/serendipity-projects/lifecycle-helper-maven-plugin/blob/master/src/it/execution-plan/pom.xml)

### Display execution plan of a project

If you run execution-plan goal without arguments all maven lifecycle/phases are displayed in the execution plan order.

```
> mvn it.serendigity.maven.plugins:lifecycle-helper-maven-plugin:execution-plan
```

![Example](images/example_execution_plan.jpg)


It is possible to indicate a specific lifecycle phase/s with parameter *lifecycle-helper.phases*.
For example if you want to list the execution plan only for *clean* and *compile* phases.

```
> mvn it.serendigity.maven.plugins:lifecycle-helper-maven-plugin:execution-plan -Dlifecycle-helper.phases=clean,compile
```
![Example](images/example_execution_plan_phases.jpg)


### Group by/Order by execution plan result
You can order and group and order the execution plan result by any column of the table filling the parameters *lifecycle-helper.groupby* *lifecycle-helper.orderby*.
Allowed values for *orderby* are the values of *MavenExecutionAttribute* enum.

Following two examples with run phases parameter *compile*:

- group by true /order by LIFECYCLE (the screenshot was taken from WINDOWS OS to show the differences with MAC OS
)
- group by true /order by PLUGIN

```
> mvn it.serendigity.maven.plugins:lifecycle-helper-maven-plugin:execution-plan -Dlifecycle-helper.orderby=LIFECYCLE -Dlifecycle-helper.groupby
```

![Example](images/example_1.jpg)

```
> mvn it.serendigity.maven.plugins:lifecycle-helper-maven-plugin:execution-plan -Dlifecycle-helper.orderby=PLUGIN -Dlifecycle-helper.groupby

```

![Example](images/example_execution_plan_group_phase.jpg)

### Filter execution plan by plugins

Moreover it's possible to filter the result to a specific plugin filling the parameter *lifecycle-helper.filter.plugins*.

```
mvn it.serendigity.maven.plugins:lifecycle-helper-maven-plugin:execution-plan -Dlifecycle-helper.filter.plugins=maven-site-plugin
```

![Example](images/example_execution_plan_filter_plugin.jpg)
