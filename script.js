// Mobile nav toggle
const navToggle = document.getElementById('navToggle');
const navLinks = document.getElementById('navLinks');

if (navToggle && navLinks) {
  navToggle.addEventListener('click', () => {
    const isOpen = navLinks.classList.toggle('open');
    navToggle.setAttribute('aria-expanded', isOpen);
  });

  navLinks.querySelectorAll('a').forEach((link) => {
    link.addEventListener('click', () => {
      navLinks.classList.remove('open');
      navToggle.setAttribute('aria-expanded', 'false');
    });
  });
}

// Ambient snowfall
(function createSnowflakes() {
  const container = document.getElementById('snowLayer');
  if (!container) return;

  const numSnowflakes = window.innerWidth < 720 ? 25 : 45;

  for (let i = 0; i < numSnowflakes; i++) {
    const flake = document.createElement('div');
    flake.classList.add('snowflake');
    const size = Math.random() * 3 + 2;
    flake.style.width = `${size}px`;
    flake.style.height = `${size}px`;
    flake.style.left = `${Math.random() * 100}vw`;
    flake.style.animationDuration = `${Math.random() * 8 + 8}s`;
    flake.style.animationDelay = `${Math.random() * 10}s`;
    container.appendChild(flake);
  }
})();
