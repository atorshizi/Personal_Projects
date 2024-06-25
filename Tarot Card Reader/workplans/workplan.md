# **Workplan 6: 06/02/24**

## **Reflections from the previous week (mini sprint)**:
- Unfortunately, our team's cloud function blocker took nearly the entire week to resolve, but was fortunately resolved before the start of this week's sprint. 

- The following is a retrospective on the use of cloud functions as a replacement to a traditional backend from a team member: \
"Severless functions are not necessarily simpler than self hosting(or cheaper at scale), they are advertised as such but we mainly used them because they are free (google wants you to use them). It introduced complicated orchestration issues that we did not anticipate being so difficult. The way authentication works and google-cloud vs firebase-client vs firebase-admin sdks that cloud functions can use do not play well with each other which is easy to forget and is not mentioned/warned in tutorials"

- Essentially the entire sprint was used to complete one task

## **Tasks for Sprint 5**:

- All left over tasks have been assigned to the final sprint and are expected to be completed before the weekly meeting on Sunday. The final webpage is expected to be deployed before Sunday 11:59 PM. 

- Documentations runs have been assigned as tasks.

- The CI/CD pipeline on the dev and main branches are planned to be fixed. Note that all branches are only merged to dev once they pass all pipeline tests within the branch. The dev and main branches have some extra tests that are broken. 

## **Changes for Sprint 5**:

- We are satisfied with our sprint process and are not making any changes. 