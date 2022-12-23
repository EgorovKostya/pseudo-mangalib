document.querySelector('.themeToggle').addEventListener('click', (event)=>{
    event.preventDefault()

    if (localStorage.getItem('theme') === 'dark-theme') {
        localStorage.removeItem('theme')
    } else {
        localStorage.setItem('theme', 'dark-theme')
    }

    addDark();
})

function addDark () {
    try {
        if (localStorage.getItem('theme') === 'dark-theme') {
            document.querySelector('body').classList.add('dark-theme');
            document.querySelector('.themeToggle span').textContent = 'clear_day'
        } else {
            document.querySelector('body').classList.remove('dark-theme');
            document.querySelector('.themeToggle span').textContent = 'bedtime';
        }
    } catch (err) {}

}
addDark();

