## A script which will help with the creation of question files
def createQuestionsFile():
    print "Enter the number of questions which are going to be in the file"

    num_questions = int(raw_input())
    questions = []

    for i in range(num_questions):
        questions.append(i)
        
        print "Enter the correct answer index for question " + str(i)
        questions.append(int(raw_input()) - 1)
    
        print "Enter the question string for question " + str(i)
        questions.append(raw_input())
    
        print "Enter answer 1 for question " + str(i)
        questions.append(raw_input())
    
        print "Enter answer 2 for question " + str(i)
        questions.append(raw_input())
    
        print "Enter answer 3 for question " + str(i)
        questions.append(raw_input())
    
        print "Enter answer 4 for question " + str(i)
        questions.append(raw_input())
    
    f = open("questions.txt", "w")

    f.write(str(num_questions) + "\n")

    for i in range(len(questions)):
        f.write(str(questions[i]) + "\n")
        
    f.close()
        
def openQuestionsFileEdit():
    f = open("questions.txt", "r+")
    question_count = int(f.readline())
    questions = []
        
    for i in range(question_count): 
        questions.append(f.readline())
        
    f.close()
        
    print "How many questions would you like to add?"
    new_count = int(raw_input())
    
    for i in range(new_count):
        questions.append(i + question_count)
    
        print "Enter the question string for question " + str(i)
        questions.append(raw_input())
    
        print "Enter answer 1 for question " + str(i)
        questions.append(raw_input())
    
        print "Enter answer 2 for question " + str(i)
        questions.append(raw_input())
    
        print "Enter answer 3 for question " + str(i)
        questions.append(raw_input())
    
        print "Enter answer 4 for question " + str(i)
        questions.append(raw_input())
    
        print "Enter the correct answer index for question " + str(i)
        questions.append(int(raw_input()) - 1)
        
    question_count += new_count
    
    f = open("questions.txt", "w")
    
    f.write(str(question_count) + "\n")

    for i in range(len(questions)):
        f.write(str(questions[i]) + "\n")
        
    f.close()
    
print "Would you like to edit the current questions file: (y/n)?"
choice = raw_input()

if (choice == 'y'):
    openQuestionsFileEdit()
else:
    createQuestionsFile()
    
    
    

    
    

