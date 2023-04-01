<h1 align="center">📚Mini library</h1>

<h2>Description</h2>

> _Simple CRUD apprication. You can:_
>
> 1. Perform CRUD-operation on books and people.
> 2. Search book by title.
> 3. Use book pagination by sending `page_number + books_per_page` parameters.
> 4. Sort books by passing `sort_per_year` parameters

<h2>Technologies</h2>

|   **Database**    |                                     [![Postgresql](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)                                     |
| :---------------: | :---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------: |
|    **Backend**    | [![Java](https://img.shields.io/badge/Java-orange?style=for-the-badge)](https://dev.java/) [![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/) |
| **View Template** |                                                     [![Jsp](https://img.shields.io/badge/Thymeleaf-008000?style=for-the-badge&logo=Thymeleaf)](https://www.thymeleaf.org)                                                     |
|  **Build Tool**   |                                             [![Maven](https://img.shields.io/badge/Maven-orange?style=for-the-badge&logo=ApacheMaven&logoColor=FF00FF)](https://maven.apache.org)                                             |
|    **Server**     |                                        [![Tomcat](https://img.shields.io/badge/Tomcat_10.*-DCDCDC?style=for-the-badge&logo=ApacheTomcat&logoColor=orange)](https://tomcat.apache.org)                                         |

---

## ⚙️ Getting Started

### 🔨 Local startup with Maven

> Java version 17+ is required.

- Clone the repository

```console
git clone https://github.com/Encerel/SpringMiniLibrary.git
```

- Import Database dump from

> _SpringMiniLibrary/src/main/resources/databasemigration/_

- Set up environment variables in

> _SpringMiniLibrary/src/main/resources/hibernate.properties.origin_

- Download Apache Tomcat 10.\* version

> *https://tomcat.apache.org/download-90.cgi*

- Deploy war arifact

- Start project using Tomcat

---

## 💠 Endpoints

<h3 align="center">👦Person</h3>

| **HTTP METHOD** |       **URL**       | **QUERY PARAMETERS** |                  **DESCRIPTION**                   |
| :-------------: | :-----------------: | :------------------: | :------------------------------------------------: |
|     **GET**     |      `/people`      |        `none`        |               Getting all `Persons`                |
|     **GET**     |   `/people/{id}`    |        `none`        |               Getting `person` by id               |
|     **GET**     |    `/people/new`    |        `none`        | Redirect to the page with a creation of a `person` |
|     **GET**     | `/people/{id}/edit` |        `none`        |   Redirect to the page with edditing a `person`    |
|    **POST**     |      `/people`      |        `none`        |               Create a new `person`                |
|    **PATCH**    |   `/people/{id}`    |        `none`        |            Update `person` information             |
|   **DELETE**    |   `/person/{id}`    |        `none`        |               Delete `person` by id                |

<h3 align="center">📔 Book</h3>

| **HTTP METHOD** |       **URL**        |                       **QUERY PARAMETERS**                        |                     **DESCRIPTION**                      |
| :-------------: | :------------------: | :---------------------------------------------------------------: | :------------------------------------------------------: |
|     **GET**     |       `/books`       |                              `none`                               |                   Getting all `books`                    |
|     **GET**     |       `/books`       |                  `sort_per_year` _not required_                   |            Getting all `books` sorted by year            |
|     **GET**     |       `/books`       |          `page_number` + `books_per_page` _not required_          |          `Books` are displayed with pagination           |
|     **GET**     |       `/books`       | `page_number` + `books_per_page` + `sort_per_year` _not required_ | `Books` are displayed with pagination and sorted by year |
|     **GET**     |    `/books/{id}`     |                              `none`                               |                   Getting `book` by id                   |
|     **GET**     |     `/books/new`     |                              `none`                               |     Redirect to the page with a creation of a `book`     |
|     **GET**     |  `/books/{id}/edit`  |                              `none`                               |       Redirect to the page with edditing a `book`        |
|     **GET**     |   `/books/search`    |                              `none`                               |            Redirect to page with search field            |
|     **GET**     |   `/books/search`    |                      `query` _not required_                       |      Getting all `books` name starting with `query`      |
|    **POST**     |       `/books`       |                              `none`                               |                   Create a new `book`                    |
|    **PATCH**    |     `/book/{id}`     |                              `none`                               |                Update `book` information                 |
|    **PATCH**    | `/book/{id}/assign`  |                              `none`                               |               Assign the `book` to person                |
|    **PATCH**    | `/book/{id}/release` |                              `none`                               |              Release the `book` from person              |
|   **DELETE**    |     `/book/{id}`     |                              `none`                               |                   Delete `book` by id                    |

---

<h2 align="center">📟 Demo</h2>

<h3 align="center">👩 Person</h3>

<h3>All people page</h3>

![](/documentation/people.png)

<h3>Person profile</h3>

_<h5>Without any books</h5>_

![](/documentation/person-profile.png)

_<h5>With several books</h5>_

![](/documentation/person-profile2.png)

<h3>Person edit profile</h3>

![](/documentation/person-edit-profile.png)

<h3>Person create</h3>

![](/documentation/person-create.png)

<h3 align="center">📗 Book</h3>

<h3>All books page</h3>

![](/documentation/books.png)

<h3>Book profile</h3>

![](/documentation/book-profile.png)

<h3>Assigned book</h3>

![](/documentation/assigned-book.png)

<h3 align="center">🔎 Search and Pagination 📑</h3>

<h3>Book search</h3>

![](/documentation/book-search.png)

<h3>Sorted by year</h3>

![](/documentation/sorted-per-year.png)

_<h3>Pagination (3 books per page)</h3>_

![](/documentation/pagination-pg-num-3-books-per-page-3.png)
