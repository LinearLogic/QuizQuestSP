## A script which will help with the creation of question files
def createQuestionsFile():
    print "Enter the number of entities to be created"

    enemy_count = int(raw_input())
    enemies = []

    for i in range(enemy_count):    
        print "Enter max HP of enemy " + str(i)
        enemies.append(int(raw_input()))
    
        print "Enter the damager of enemy " + str(i)
        enemies.append(int(raw_input()))
    
        print "Enter the item number of enemy " + str(i)
        enemies.append(int(raw_input()))
    
        print "Enter the item attribute(damage for spell, lock id for key, etc.) " + str(i)
        enemies.append(int(raw_input()))
    
        print "Enter x coordinate of enemy " + str(i)
        enemies.append(int(raw_input()))
    
        print "Enter y coordinate of enemy " + str(i)
        enemies.append(int(raw_input()))
        
        print "Enter the question id of enemy" + str(i)
        enemies.append(int(raw_input))
    
    f = open("enemies.txt", "w")

    f.write(str(enemy_count) + "\n")

    for i in range(len(enemies)):
        f.write(str(enemies[i]) + "\n")
        
    f.close()
        
def openQuestionsFileEdit():
    f = open("enemies.txt", "r+")
    enemy_count = int(f.readline())
    enemies = []
        
    for i in range(enemy_count): 
        questions.append(f.readline())
        
    f.close()
        
    print "How many enemies would you like to add?"
    new_count = int(raw_input())
    
    for i in range(enemy_count):    
        print "Enter max HP of enemy " + str(i)
        enemies.append(int(raw_input()))
    
        print "Enter the damager of enemy " + str(i)
        enemies.append(int(raw_input()))
    
        print "Enter the item number of enemy " + str(i)
        enemies.append(int(raw_input()))
    
        print "Enter the item attribute(damage for spell, lock id for key, etc.) " + str(i)
        enemies.append(int(raw_input()))
    
        print "Enter x coordinate of enemy " + str(i)
        enemies.append(int(raw_input()))
    
        print "Enter y coordinate of enemy " + str(i)
        enemies.append(int(raw_input()))
        
        print "Enter the question id of enemy" + str(i)
        enemies.append(int(raw_input))
        
    enemy_count += new_count
    
    f = open("enemies.txt", "w")
    
    f.write(str(enemy_count) + "\n")

    for i in range(len(enemies)):
        f.write(str(enemies[i]) + "\n")
        
    f.close()
    
print "Would you like to edit the current enemies file: (y/n)?"
choice = raw_input()

if (choice == 'y'):
    openQuestionsFileEdit()
else:
    createQuestionsFile()