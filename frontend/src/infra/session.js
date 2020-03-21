export function getSession() {
    const userId = localStorage.getItem("userId");
    if (!!userId) {
        const expires = new Date(localStorage.getItem("expires"));
        return {
            active: expires > new Date(),
            userId: userId
        }
    }
    return {
        active: false
    }
}

export function saveSession(session) {
    localStorage.setItem("userId", session.userId);
    localStorage.setItem("expires", session.expires);
}

export function deleteSession() {
    localStorage.removeItem("userId");
    localStorage.removeItem("expires");
}