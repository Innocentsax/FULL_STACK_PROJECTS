

using System.Timers;
namespace EFarm.Server.Services
{
    public class ToastService : IDisposable
    {
        public event Action<string, ToastLevel>? OnShow;
        public event Action? OnHide;
        private System.Timers.Timer? Countdown;

        private void ShowToast(string message, ToastLevel level)
        {
            OnShow?.Invoke(message, level);
            StartCountdown();
        }
        public void Success(string message)=>ShowToast(message, ToastLevel.Success);
        public void Error(string message)=>ShowToast(message, ToastLevel.Error);
        public void Warning(string message)=>ShowToast(message, ToastLevel.Warning);
        public void Info(string message)=>ShowToast(message, ToastLevel.Info);


        private void StartCountdown()
        {
            SetCountdown();

            if (Countdown!.Enabled)
            {
                Countdown.Stop();
                Countdown.Start();
            }
            else
            {
                Countdown!.Start();
            }
        }

        private void SetCountdown()
        {
            if (Countdown != null) return;

            Countdown = new System.Timers.Timer(2000);
            Countdown.Elapsed += HideToast;
            Countdown.AutoReset = false;
        }

        private void HideToast(object? source, ElapsedEventArgs args)
            => OnHide?.Invoke();

        public void Dispose()
            => Countdown?.Dispose();
    }

    public enum ToastLevel
    {
        Info,
        Success,
        Warning,
        Error
    }
}
