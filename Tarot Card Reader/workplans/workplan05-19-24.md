# ***Workplan 4: 05/19/24***

## **Reflections from Sprint 2**:

- Most stories from the previous sprint were closed, with the exception of a couple of small tasks. These tasks are planned to be finished during the early portions of the current sprint. 

- Some test related tasks were closed without significant progess. A couple of factors lead to this decision:
    - Some tasks could not be feasibly tested. 
    - Testing is currently broken on the CI/CD pipeline. We are holding off on adding new tests until this can be fixed during this sprint. 
    
## **Changes for Sprint 3**:

- While we planned to assign tasks specifically to individuals in the previous sprint, we did not go forward with this decision. Instead we only assigned individuals to to user stories and allowed tasks to be self assigned. This continued to cause some hesitiation to start tasks, so we are now specifically assigning tasks to individuals. 

- Due to the large number of programmers on the team, we are comfortable continuing to assign the same priority to all the tasks. However, we will keep everyone aware on which tasks might benefit from being completed earlier or later in the sprint. Some examples are:
    - Bugfixes and CI/CD changes being completed earlier in the sprint.
    - Tasks the might require file system changes or tasks such as hosting being completed after code changes at the end of the split. 

## **New Tasks for Sprint 3**:

1. Bugfixes for the UI:
    - We observed a few UI bugs during the previous sprint. Each observed bug is listed as a task and scheduled to be fixed early in the sprint. 

2. Limiting Access to the Firebase API key and Vertex AI:
    - We will be implementing best practice and securing our API keys to ensure they are secured/cannot be abused in bad faith. 
    - Security rules for the FIrebase API key will be reviewed.
    - We will be calling Vertex AI through a REST API to prevent the client from accessing and modifying the prompt. 

3. Daily History Calendar:
    - We will be adding a weekly calendar to the history tab for users to view the fortunes generated in the calendar week. 
    - The creation of the frontend and backend are seperated. Conencting the backend and frontend will be required for the the backend task to be closed. 
    - We will also be implementing limiting the user to one daily fortune per day. 