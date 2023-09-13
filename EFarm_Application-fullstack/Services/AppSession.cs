
namespace EFarm.Server.Services
{
	public class AppSession:IAppSession
	{
		readonly IHttpContextAccessor _contextAccessor;

		public AppSession(IHttpContextAccessor contextAccessor)
		{
			_contextAccessor = contextAccessor;
		}

		public string UserId
		{
			get
			{
				if (_contextAccessor.HttpContext == null || _contextAccessor.HttpContext.Request==null)
				throw new Exception("No httpcontext found.");
				var headers = _contextAccessor.HttpContext.Request.Headers;
				if (headers != null)
				{
					var userId = headers["userId"];
					if (!string.IsNullOrEmpty(userId))
						return userId;

				}
				 
					throw new Exception("No User found.");
				 
				 
			}
		
		}

		 
	}
}
