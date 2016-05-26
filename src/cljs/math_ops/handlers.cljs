(ns math-ops.handlers
  (:require
    [re-frame.core :as re-frame]
    [math-ops.operations-guessing :as operations-guessing]))

(re-frame/register-handler
  :initialize-state
  (fn [_ _]
    {:current-level :max-level
     :guessing      (operations-guessing/start-new-guessing :max-level)
     :history       []}))

(defn success-or-failure?
  [{current-guessing-input :number-input}
   {new-guessing-input :number-input}]
  (and
    (not= current-guessing-input "?")
    (= new-guessing-input "?")))

(re-frame/register-handler
  :press-key
  (fn [{:keys [history guessing current-level] :as state} [_ key-code]]
    (let [current-guessing (:guessing state)
          new-guessing (operations-guessing/process-input guessing current-level key-code)]
      (assoc
        (assoc state :history (if (success-or-failure? current-guessing new-guessing)
                                (conj history current-guessing)
                                history))
        :guessing

        new-guessing))))

(re-frame/register-handler
  :select-level
  (fn [state [_ key-level]]
    (-> state
        (assoc :current-level key-level)
        (assoc :guessing (operations-guessing/start-new-guessing key-level)))))

