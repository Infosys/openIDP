

# Contribute code to IDP Open Source

### Step 1: Fork the Project to your local system
**i.	 Fork & Clone**

•	Fork the repository from GitHub to have your copy of openIDP in your GitHub account 

•	Clone the repository with HTTPs URL https://github.com/<yourusername>/openIDP.git

````
git clone git@github.com:username/ openIDP.git
e.g.  git clone https://github.com/InfyMegha/openIDP.git
cd openIDP
````

Now, you have a local copy of your fork of the openIDP repository!

### Step 2:  Branch to put changes

**i. Create branch**

   To create a branch, type the following (spaces won't be recognized in the branch name, so you will need to use a hyphen or underscore):
````
git checkout -b NAME-OF-BRANCH
e.g.  git checkout –b My_Feature
````

**ii.   Work on an existing branch**

To switch to an existing branch, so you can work on it:

````
git checkout NAME-OF-BRANCH
e.g. git checkout My_Feature
````

### Step 3:  View the changes you've made 

It's important to be aware of what's happening and the status of your changes. When you add, change, or delete files/folders, Git knows about it. To check the status of your changes:

````
git status
````

**View differences**

To view the differences between your local, unstaged changes and the repository versions that you cloned or pulled, type:
````
git diff
````

### Step 4: Commit

i. Use _**git add**_ to stage a local file/folder for committing. Then use _**git commit**_ to commit the staged files:

````
git add FILE  or git add . (To add all changes)
e.g  git add input.html  or git add .
git commit -m "COMMENT TO DESCRIBE THE INTENTION OF THE COMMIT"
e.g. git commit –m “Add input file to repository”
````
 
### Step 5:Push the changesTo push your changes to local repository

````
git push <Remote_Name><Branch_Name>
e.g.  git push origin My_feature
````

### Step 6: Creating a pull request from a fork

Pull the desired branch from the upstream repository. This method will retain the commit history
without modification.

````
git pull https://github.com/ORIGINAL_OWNER/ORIGINAL_REPOSITORY.git BRANCH_NAME
e.g. git pull https://github.com/InfyMegha/openIDP.git My_Changes
````
If you've forked a repository and made changes to the fork, you can ask that the upstream repository accept your changes by creating a pull request.

i.	Navigate to the original repository you created your fork from.

ii.	To the right of the Branch menu, click New pull request.

iii.	On the Compare page, click **compare across forks**.

![](https://github.com/Infosys/openIDP/blob/master/docs/create_pull_request.png)

iv.	Confirm that the base fork is the repository you'd like to merge changes into. Use the base branch drop-down menu to select the branch of the upstream repository you'd like to merge changes into.

v.	Use the head fork drop-down menu to select your fork, then use the compare branch drop-down menu to select the branch you made your changes in.

vi.	Type a title and description for your pull request.

vii.	Click Create **pull request**.

![](https://github.com/Infosys/openIDP/blob/master/docs/create_pull_request_2.png)
 

### Step 7: Sign the CLA

Before we can accept the pull request, we first request people to sign a Contributor License Agreement (or CLA). Download the agreement PDF file (CLA_IDP.pdf) from the repository and sign and send it to IDP_OSS@domain.com

### Step 8: Pull request closure

Before we can accept the pull request, we check code quality through Codacy along with other parameters such as unit tests, code coverage, documentation and functional test results. 
