(ns villagebook.auth.core
  (:require [buddy.auth.backends.token :as backend]
            [buddy.auth.protocols :as proto]))

(defn custom-backend
  "Reads token from cookies (if present) and passes it to buddy-auth's jws-backend."
  [config-fn]
  (reify
    proto/IAuthentication
    (-parse [_ request]
      (or
       (get-in request [:cookies "token" :value])
       (.-parse (backend/jws-backend (config-fn)) request)))
    (-authenticate [_ request token]
      (.-authenticate (backend/jws-backend (config-fn)) request token))

    proto/IAuthorization
    (-handle-unauthorized [_ request metadata]
      (.-handle-unauthorized (backend/jws-backend (config-fn)) request metadata))))
