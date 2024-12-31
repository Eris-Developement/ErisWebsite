const discordId = "yumpty";

const discordButton = document.getElementById('discord_button');
const notificationContainer = document.querySelector('.notification-container');

function showNotification(message) {
    const notification = document.createElement('div');
    notification.className = 'notification';
    notification.textContent = message;

    notificationContainer.appendChild(notification);

    setTimeout(() => {
        notification.remove();
    }, 5000);
}

discordButton.addEventListener('click', () => {
    showNotification('Discord id was copied to clipboard.');
    navigator.clipboard.writeText(discordId);
});