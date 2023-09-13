namespace EFarm.Server
{
	public class AppState
	{
		public event Action? OnStateChange;
		public void TriggerChange() => NotifyStateChanged();
		private void NotifyStateChanged() => OnStateChange?.Invoke();
	}
}
