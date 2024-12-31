document.addEventListener('DOMContentLoaded', () => {
    const starsContainer = document.querySelector('.stars');

    for (let i = 0; i < 100; i++) {
        const star = document.createElement('div');
        star.classList.add('star');
        star.style.left = `${Math.random() * 100}%`;
        star.style.top = `${Math.random() * 100}%`;
        star.style.animationDelay = `${Math.random() * 3}s`;
        starsContainer.appendChild(star);
    }
});

window.addEventListener('scroll', function() {
    const footer = document.querySelector('.site-footer');

    // When scrolled down more than 100px, show the footer
    if (window.scrollY > 100) {
        footer.classList.add('visible');
    } else {
        footer.classList.remove('visible');
    }
});