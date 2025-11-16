#### **6.**





**6.1 How Room handles database schema migration ?**



**When you change the database version, Room checks the version number.**

**If no migration strategy is defined, Room will delete the existing data and recreate the database which causes data loss.**





**6.2**



@Database(entities = [Utilisateur::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun utilisateurDao(): UtilisateurDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {

                database.execSQL("ALTER TABLE utilisateurs ADD COLUMN age INTEGER NOT NULL DEFAULT 0")
            }
        }


#### **7.**

#### 

#### **Key Steps** 



1\. Room Database Configuration



\- Created a User entity with fields: id, name, email

\- Defined a DAO interface with basic CRUD operations

\- Configured a singleton AppDatabase to manage the database instance

\- Used the Singleton pattern to avoid multiple connections



2\. CRUD Operations Implementation

\- Create: Added new users via a modal dialog

\- Read: Displayed the full list of users

\- Delete: Enabled deletion of individual users



3\. User Interface with Jetpack Compose

\- Built a main screen using LazyColumn to show the user list

\- Implemented a FloatingActionButton to add users

\- Designed a custom dialog for input

\- Applied Material Design 3 with modern components



4\. Concurrency Management

\- Used Kotlin coroutines for asynchronous operations

\- Separated threads: UI on Main, database on IO

\- Wrapped database calls in withContext(Dispatchers.IO) to prevent UI blocking



5\. Schema Migration

\- Added an age column to the existing table

\- Created a Migration object to handle version upgrade from 1 to 2

\- Preserved existing data during migration



#### **Challenges**



Challenge 1: Room Compilation Errors



Issue: “Not sure how to convert a Cursor to this method's return type (java.lang.Object)”

Cause: DAO functions were marked suspend, causing mapping issues with Room in certain setups

Solution: Removed suspend from DAO methods and handled async calls using withContext(Dispatchers.IO) in the Activity



Challenge 2: Non-Reactive UI



Issue: Database changes weren’t reflected immediately in the UI

Cause: Missing state update mechanism after CRUD operations

Solution: Used mutableStateOf with remember to create reactive state variables. Refreshed the list after each CRUD operation:

users = withContext(Dispatchers.IO) { dao.getAllUsers() }



Challenge 3: UI Freezing



Issue: App would freeze during database operations

Cause: Room operations were running on the main thread

Solution: Wrapped all DAO operations in withContext(Dispatchers.IO) to run them on a dedicated I/O thread

