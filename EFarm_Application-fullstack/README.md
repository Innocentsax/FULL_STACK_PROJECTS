# EFarm E-Commerce Website

<img src="https://github.com/opius2017/EFarm_Application/blob/master/Efarm%20logo.png" width="1000" height="250">

EFarm is an e-commerce website built by a Team of 3 Developers as a Blazor project. It leverages the power of 
Blazor, a Microsoft framework that enables developers to create interactive client-side web UIs using 
C# instead of JavaScript. This repository contains the codebase for the EFarm e-commerce website. 
Follow the steps below to obtain a local copy of the sample apps and explore the codebase.

## Getting Started

To obtain a local copy of the EFarm e-commerce website, you have two options:

1. **Fork and Clone:**
   - Fork this repository to your GitHub account.
   - Clone the forked repository to your local system using your preferred Git client.
   
2. **Download ZIP:**
   - Click the "Code" button at the top of this repository.
   - Select "Download ZIP" to save the repository as a compressed archive.
   - Extract the downloaded ZIP archive to your local system.

  
<img src="https://github.com/opius2017/EFarm_Application/blob/master/Product%20page.png" width="1000" height="250">


## About Blazor

Blazor is a cutting-edge framework developed by Microsoft for building interactive client-side web UIs with .NET. Some key features and advantages of Blazor include:

- Write rich interactive UIs using C# instead of JavaScript.
- Share app logic written in .NET between server-side and client-side.
- Render UI as HTML and CSS for broad browser compatibility.
- Integrate with modern hosting platforms like Docker.
- Build hybrid desktop and mobile apps using .NET and Blazor.

  

<img src="https://github.com/opius2017/EFarm_Application/blob/master/Body%20Page.png" width="1000" height="250">


## Why Use Blazor for Client-Side Web Development

Using .NET for client-side web development offers numerous advantages:

- Write code in C# instead of JavaScript.
- Leverage the extensive .NET ecosystem of libraries.
- Share app logic between server and client.
- Benefit from .NET's performance, reliability, and security.
- Stay productive on Windows, Linux, or macOS using Visual Studio or Visual Studio Code.
- Build on a stable and feature-rich set of languages, frameworks, and tools.


<img src="https://github.com/opius2017/EFarm_Application/blob/master/Landing%20page.png" width="1000" height="250">


## Blazor Components and Razor Markup

Blazor uses Razor markup to combine HTML and C# code within the same file. Components, formally known 
as Razor components and informally as Blazor components, play a key role in building UIs. Components 
render into an in-memory representation of the browser's Document Object Model (DOM) called a render tree, facilitating efficient UI updates.

Here's an example of a simple component, `Dialog.razor`, that displays a dialog and processes a button click event:

```csharp
@Title
@ChildContent

<button @onclick="OnYes">Yes!</button>

@code {
    [Parameter] public RenderFragment? ChildContent { get; set; }
    [Parameter] public string? Title { get; set; }

    private void OnYes()
    {
        Console.WriteLine("Write to the console in C#! 'Yes' button selected.");
    }
}
```

## Blazor Server

Blazor Server is a hosting model that supports running Razor components on the server within an ASP.NET Core app. 
UI updates are handled over a SignalR connection. The runtime remains on the server and manages various tasks, 
including executing C# code, handling UI events from the browser, and applying UI updates to components.

## User Sessions and Cookies

User sessions are managed using cookies in EFarm. The `AppSession` class in the `EFarm.Api.Services` 
namespace handles user sessions. It uses an `IHttpContextAccessor` to access the HttpContext and manage cookies. 
The `UserId` property generates a unique user ID and stores it in a "user" cookie. If the cookie doesn't exist, 
a new user ID is created and stored. Otherwise, the stored user ID is retrieved.


<img src="https://github.com/opius2017/EFarm_Application/blob/master/Payment.png" width="1000" height="300">


## BasketService for User Baskets

The `BasketService` class in the `EFarm.Api.Services` namespace manages user baskets in the application. 
It handles adding items to a basket, counting total basket items, and creating or retrieving baskets for users. 
It interacts with the `AppDbContext` for database operations and `IAppSession` for session information.

## Basket Class for Shopping Baskets

The `Basket` class in the `EFarm.Api.Model` namespace represents a user's shopping basket. It inherits from 
`BaseEntity` and provides functionalities to manage items in the basket. It includes methods to add items, remove empty items, and update the buyer ID.

## Generic Repository for Data Access

The `Repository` class serves as a data access layer, following the repository pattern. It provides CRUD operations 
for interacting with entities in the database. Generics allow it to work with various entity types, promoting reusability and separation of concerns.


<img src="https://github.com/opius2017/EFarm_Application/blob/master/add%20to%20cart.png" width="1000" height="300">


## Shopping Cart Component

Blazor components are used to display the shopping cart icon with an item count badge. The component interacts 
with the server to retrieve and display the count of items in the user's shopping cart. Real-time updates are achieved 
through the `OnStateChange` event of the `AppState` service.

## Conclusion

The EFarm e-commerce website showcases the power of Blazor for building interactive and feature-rich web applications using 
.NET. With a combination of Blazor components, Razor markup, and robust backend services, EFarm offers a seamless shopping experience to users.

Feel free to explore the codebase, contribute, and customize EFarm to suit your needs!

For more information or inquiries, contact the Alx Group at [contact@alxgroup.com](mailto:contact@alxgroup.com).



<img src="https://github.com/opius2017/EFarm_Application/blob/master/Efarm%20view.png" width="1000" height="500">




<!--
# EFarm e-commerce website
## An Alx group project
To obtain a local copy of the sample apps in this repository, use either of the following approaches:
1. Fork this repository and clone it to your local system.
2. Select the Code button. Select Download ZIP to save the repository locally. Extract the saved Zip archive (.zip) to access the sample apps.

Blazor, a Microsoft framework for building interactive client-side web UI with .NET:
•	Create rich interactive UIs using C# instead of JavaScript.
•	Share server-side and client-side app logic written in .NET.
•	Render the UI as HTML and CSS for wide browser support, including mobile browsers.
•	Integrate with modern hosting platforms, such as Docker.
•	Build hybrid desktop and mobile apps with .NET and Blazor.

Using .NET for client-side web development offers the following advantages:
•	Write code in C# instead of JavaScript.
•	Leverage the existing .NET ecosystem of .NET libraries.
•	Share app logic across server and client.
•	Benefit from .NET's performance, reliability, and security.
•	Stay productive on Windows, Linux, or macOS with a development environment, such as Visual Studio or Visual Studio Code.
•	Build on a common set of languages, frameworks, and tools that are stable, feature-rich, and easy to use.

The component class is usually written in the form of a Razor markup page with a .razor file extension. Components in Blazor are formally referred to as Razor components, informally as Blazor components. Razor is a syntax for combining HTML markup with C# code designed for developer productivity. Razor allows you to switch between HTML markup and C# in the same file with IntelliSense programming support in Visual Studio. Razor Pages and MVC also use Razor. Unlike Razor Pages and MVC, which are built around a request/response model, components are used specifically for client-side UI logic and composition.

Blazor uses natural HTML tags for UI composition. The following Razor markup demonstrates a component (Dialog.razor) that displays a dialog and processes an event when the user selects a button:

<div class="card" style="width:22rem">
    <div class="card-body">
        <h3 class="card-title">@Title</h3>
        <p class="card-text">@ChildContent</p>
        <button @onclick="OnYes">Yes!</button>
    </div>
</div>

@code {
    [Parameter]
    public RenderFragment? ChildContent { get; set; }

    [Parameter]
    public string? Title { get; set; }

    private void OnYes()
    {
        Console.WriteLine("Write to the console in C#! 'Yes' button selected.");
    }
}


Components render into an in-memory representation of the browser's Document Object Model (DOM) called a render tree, which is used to update the UI in a flexible and efficient way.

Blazor Server

Blazor Server provides support for hosting Razor components on the server in an ASP.NET Core app. UI updates are handled over a SignalR connection.
The runtime stays on the server and handles:
•	Executing the app's C# code.
•	Sending UI events from the browser to the server.
•	Applying UI updates to a rendered component that are sent back by the server.
The connection used by Blazor Server to communicate with the browser is also used to handle JavaScript interop calls.
 

Cookies
Cookies were created by the application and passed to the user’s web browser when the user submits the request. The web browser passes the cookie back to the application to indicate that the user is authenticated. When the user logs out, the cookie is removed.
Configure the cookie authentication services in the appSession.cs file.

namespace EFarm.Api.Services
{
    // Define a class named AppSession that implements the IAppSession interface.
    public class AppSession : IAppSession
    {
        // Declare a private readonly field of type IHttpContextAccessor.
        private readonly IHttpContextAccessor _contextAccessor;

        // Constructor that takes an IHttpContextAccessor as a parameter.
        public AppSession(IHttpContextAccessor contextAccessor)
        {
            // Initialize the private field with the provided contextAccessor.
            _contextAccessor = contextAccessor;
        }

        // Property to get the UserId.
        public string UserId
        {
            get
            {
                // Generate a new unique identifier (Guid) as the userId.
                var userId = $"{Guid.NewGuid()}";

                // Check if the HttpContext or the Request is null.
                if (_contextAccessor.HttpContext == null || _contextAccessor.HttpContext.Request == null)
                {
                    // If HttpContext or Request is null, create a user with the generated userId and return it.
                    CreateUser(userId);
                    return userId;
                }

                // If HttpContext and Request are not null, retrieve the current request.
                var request = _contextAccessor.HttpContext.Request;

                // Check if the "user" cookie is not present in the request.
                if (request.Cookies["user"] == null)
                {
                    // If the "user" cookie is not present, create a user with the generated userId and return it.
                    CreateUser(userId);
                    return userId;
                }
                else
                {
                    // If the "user" cookie is present, return its value (userId).
                    return request.Cookies["user"];
                }
            }
        }

        // Private method to create a user by appending a "user" cookie to the response.
        private void CreateUser(string userId)
        {
            // Check if the HttpContext is not null.
            if (_contextAccessor.HttpContext != null)
            {
                // If HttpContext is not null, append a "user" cookie with the provided userId to the response.
                _contextAccessor.HttpContext.Response.Cookies.Append("user", userId);
            }
        }
    }
}

The C# code above defines a class named AppSession within the EFarm.Api.Services namespace. The purpose of this class is to manage user sessions in a web application using ASP.NET Core. This code defines a AppSession class that implements the IAppSession interface. The class is responsible for managing user sessions and generating unique user IDs. It uses an IHttpContextAccessor to access the HttpContext and manage cookies. The UserId property generates a unique user ID and stores it in a cookie named "user" within the HttpContext. If the "user" cookie is not present in the request, a new user ID is created and stored in the cookie. If the cookie is already present, the stored user ID is retrieved and returned. This approach allows for maintaining user sessions and managing user identification within the application.



The code below represents a service class named BasketService that handles the management of user baskets in the application. It encapsulates the logic for adding items to a basket, counting total basket items, and retrieving or creating baskets for users. It interacts with the AppDbContext for database operations and IAppSession for retrieving user-specific session information. The comments explains more on the purpose and functionality of each method and component within the class:

using EFarm.Api.Data;
using EFarm.Api.Model;
using Microsoft.EntityFrameworkCore;

namespace EFarm.Api.Services
{
    // This class implements the IBasketService interface and provides functionality for managing user baskets.
    public class BasketService : IBasketService
    {
        // Reference to the application's database context.
        readonly AppDbContext _dbContext;
        
        // Reference to the session service for retrieving user-specific session information.
        readonly IAppSession _session;

        // Constructor to initialize the BasketService with required dependencies.
        public BasketService(AppDbContext dbContext, IAppSession session)
        {
            _dbContext = dbContext;
            _session = session;
        }

        // Adds an item to the user's basket asynchronously.
        public async Task AddItem(int productId, decimal unitPrice, int quantity = 1)
        {
            // Retrieve or create the user's basket.
            var basket = await GetOrCreateBasketForUser();

            // Add the item to the basket.
            basket.AddItem(productId, unitPrice, quantity);

            // Update the basket in the database.
            _dbContext.Baskets.Update(basket);
            await _dbContext.SaveChangesAsync();
        }

        // Counts the total number of items in the user's basket asynchronously.
        public async Task<int> CountTotalBasketItems()
        {
            // Query the database to calculate the total items in the user's basket.
            var totalItems = await _dbContext.Baskets
                .Where(basket => basket.BuyerId == _session.UserId)
                .SelectMany(item => item.Items)
                .SumAsync(sum => sum.Quantity);

            return totalItems;
        }

        // Retrieves an existing user's basket or creates a new one asynchronously.
        public async Task<Basket> GetOrCreateBasketForUser()
        {
            // Get the user's ID from the session.
            var userId = _session.UserId;

            // Attempt to retrieve the user's basket from the database.
            var basket = await GetUserBasket(userId);

            // If the user's basket doesn't exist, create a new one.
            if (basket == null)
            {
                return await CreateBasketForUser(userId);
            }

            return basket;
        }

        // Creates a new basket for a user asynchronously.
        private async Task<Basket> CreateBasketForUser(string userId)
        {
            // Create a new Basket object and add it to the database.
            var basket = new Basket(userId);
            await _dbContext.Baskets.AddAsync(basket);
            await _dbContext.SaveChangesAsync();

            return basket;
        }

        // Retrieves a user's basket from the database with its associated items.
        private async Task<Basket> GetUserBasket(string userId)
        {
            // Retrieve the user's basket along with its associated items from the database.
            return await _dbContext.Baskets.Include(x => x.Items).FirstOrDefaultAsync(x => x.BuyerId == userId);
        }
    }
}

The code below defines a C# class named Basket, which represents a user's shopping basket in the e-commerce application. The class inherits from BaseEntity, which explains that it is part of an entity framework model.

Here's a breakdown of the main components and functionalities of the ‘Basket’ class:

1.	BuyerId: A public property to store the ID of the buyer who owns the basket.
2.	_items: A private list field of BasketItem objects, used to store the items added to the basket.
3.	Items: A public property that exposes the list of BasketItem objects.
4.	TotalItems: A public property that calculates the total quantity of items in the basket by summing the quantities of individual items.
5.	Constructor: Initializes a new Basket object with a specified buyerId.
6.	AddItem method: Adds an item to the basket. If an item with the same productId doesn't exist, a new BasketItem is added to the list. If an item with the same productId already exists, its quantity is updated.
7.	RemoveEmptyItems method: Removes items from the basket that have a quantity of zero.
8.	SetNewBuyerId method: Sets a new buyer ID for the basket.

The Basket class is designed to manage the items in a user's shopping basket, allowing items to be added, quantities adjusted, empty items removed, and buyer information updated. It provides a useful representation of a key feature in the e-commerce application.

using EFarm.Api.Model;

// The namespace declaration for the EFarm.Api.Model, which is used in the code.
namespace EFarm.Api.Model
{
    // A class representing a user's shopping basket, inheriting from BaseEntity.
    public class Basket : BaseEntity
    {
        // Public property to store the ID of the buyer.
        public string BuyerId { get; set; }

        // A private list field to store individual BasketItem objects.
        private readonly List<BasketItem> _items = new List<BasketItem>();

        // Public property to expose the list of BasketItem objects.
        public List<BasketItem> Items { get; set; }

        // Public property to calculate the total number of items in the basket.
        public int TotalItems => _items.Sum(i => i.Quantity);

        // Constructor to create a Basket object with a specified buyer ID.
        public Basket(string buyerId)
        {
            BuyerId = buyerId;
        }

        // Method to add an item to the basket based on the provided parameters.
        public void AddItem(int productId, decimal unitPrice, int quantity = 1)
        {
            // Check if an item with the same productId exists in the basket.
            if (!Items.Any(i => i.ProductId == productId))
            {
                // If not, add a new BasketItem to the list.
                _items.Add(new BasketItem(productId, quantity, unitPrice));
                return;
            }
            
            // If an item with the same productId exists, update its quantity.
            var existingItem = Items.First(i => i.ProductId == productId);
            existingItem.AddQuantity(quantity);
        }

        // Method to remove items with zero quantity from the basket.
        public void RemoveEmptyItems()
        {
            _items.RemoveAll(i => i.Quantity == 0);
        }

        // Method to set a new buyer ID for the basket.
        public void SetNewBuyerId(string buyerId)
        {
            BuyerId = buyerId;
        }
    }
}

A generic Repository class is created to serves as a data access layer for interacting with entities in the database. The Repository class follows the repository pattern, providing a standardized way to perform CRUD (Create, Read, Update, Delete) operations on entities in the database. It helps encapsulate database interactions and promotes separation of concerns within the application. The usage of generics allows it to work with different types of entities, providing a flexible and reusable data access solution.

Blazor components are used for displaying the shopping cart icon with an item count badge. The component interacts with the server to retrieve and display the count of items in the user's shopping cart. These component were added to a Blazor page and layout. When a user interacts with the shopping cart (e.g., adds or removes items), the OnStateChange event of the appState service is triggered. This, in turn, updates the displayed item count in real-time without requiring a full page refresh.
-->
