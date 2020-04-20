(ns user.garden.patch)


(defmacro defstylesfn
  "Convenience macro equivalent to `(defn name bindings (list styles*))`."
  [name & xs]
  (let [x          (first xs)
        docstring? (string? x)
        [docstring
         bindings
         styles]   (if docstring?
                     [x (second xs) (nnext xs)]
                     ["" x (next xs)])]
    `(defn ~name ~docstring ~bindings (list ~@styles))))


(defmacro defstylesheetfn
  "Convenience macro equivalent to `(defn name [binding] (css binding styles*))`.

  `binding` is garden.core/css option binding"
  [name [binding] & styles]
  `(defn ~name [opt#]
     (let [~binding opt#]
       (garden.core/css opt# ~@styles))))
