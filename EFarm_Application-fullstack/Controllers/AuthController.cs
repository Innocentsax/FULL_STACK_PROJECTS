using Microsoft.AspNetCore.Mvc;
using Microsoft.IdentityModel.Tokens;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;

// Import necessary libraries for this controller

// Define the controller within the 'EFarm.Server.Controllers' namespace
namespace EFarm.Server.Controllers
{
	// Define the route for this controller, making it accessible at '/api/authenticate'
	[Route("/api/authenticate")]

	// Indicate that this class is a controller for handling HTTP requests
	[ApiController]

	// Create a class named 'AuthController' that inherits from 'ControllerBase'
	public class AuthController : ControllerBase
	{
		// Define a private field to hold the configuration settings
		private readonly IConfiguration _configuration;

		// Constructor for 'AuthController' that receives an 'IConfiguration' instance
		public AuthController(IConfiguration configuration)
		{
			// Initialize the '_configuration' field with the provided configuration instance
			_configuration = configuration;
		}

		// Define an HTTP POST method called 'Login' that takes a 'userId' as input
		[HttpPost]
		public IActionResult Login(string userId)
		{
			// Validate user credentials (not shown here)

			// Create an array of claims to represent user identity information
			var claims = new[]
			{
                // Add a claim with the name "userId" and the value of the provided 'userId'
                new Claim("userId", userId)
                // You can add more claims here if needed...
            };

			// Create a symmetric security key using the 'Jwt:Secret' value from configuration
			var key = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_configuration["Jwt:Secret"]));

			// Create signing credentials using the security key and HMAC-SHA256 algorithm
			var creds = new SigningCredentials(key, SecurityAlgorithms.HmacSha256);

			// Create a JWT (JSON Web Token) with specified parameters
			var token = new JwtSecurityToken(
				// Token issuer (usually your application's name)
				issuer: _configuration["Jwt:Issuer"],
				// Token audience (usually the intended audience for the token)
				audience: _configuration["Jwt:Audience"],
				// Claims representing user identity
				claims: claims,
				// Token expiration time (e.g., 30 minutes from now)
				expires: DateTime.Now.AddMinutes(30),
				// Signing credentials
				signingCredentials: creds);

			// Return an HTTP OK response with a JSON object containing the JWT token
			return Ok(new
			{
				// Serialize and include the token in the response
				token = new JwtSecurityTokenHandler().WriteToken(token)
			});
		}
	}
}