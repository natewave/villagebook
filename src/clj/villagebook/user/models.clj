(ns villagebook.user.models
  (:require [buddy.hashers :as hasher]
            [buddy.auth :refer [authenticated? throw-unauthorized]]
            [buddy.sign.jwt :as jwt]

            [villagebook.user.db :as db]
            [villagebook.config :as config]))

(defn create!
  [{:keys [id] :as userdata}]
  (if-not (db/retrieve id)
    (let [user (db/create! userdata)]
      {:success user})
    {:error "User with this email already exists."}))

(defn get-token
  [email password]
  (let [{hashed-password :password
         db-email        :email
         id              :id} (db/retrieve-by-email email)
        token    (jwt/sign {:email db-email :id id} (config/jwt-secret))]
    (if id
      (if (hasher/check password hashed-password)
        {:success {:token token}}
        {:error "Invalid password"})
      {:error "Email not found"})))

(defn retrieve
  [id]
  (dissoc (db/retrieve id) :password :created_at :id))

(defn retrieve-by-email
  [email]
  (dissoc (db/retrieve-by-email email) :password :created_at :id))
