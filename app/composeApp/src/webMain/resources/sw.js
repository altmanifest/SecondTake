self.addEventListener('install', _ => {
    console.log('Service worker installed')
})

self.addEventListener('activate', _ => {
    console.log("Service worker activated")
})