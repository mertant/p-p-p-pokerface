(ns p-p-p-pokerface)

(def char-ranks {\T 10,
                 \J 11,
                 \Q 12,
                 \K 13,
                 \A 14})

(defn rank [card]
  (let [[fst _] card]
    (if (Character/isDigit fst)
      (Integer/valueOf (str fst))
      (char-ranks fst))))

(defn suit [card]
  (let [[_ snd] card]
    (str snd)))

(defn hand-set-sizes [hand]
  (let [ranks (map rank hand)
        freqs (vals (frequencies ranks))]
    freqs))

(defn contains-set-of-size? [hand size]
  (let [set-sizes (hand-set-sizes hand)]
    (not (empty? (filter (fn [x] (= x size)) set-sizes)))))

(defn pair? [hand]
  (contains-set-of-size? hand 2))

(defn three-of-a-kind? [hand]
  (contains-set-of-size? hand 3))

(defn four-of-a-kind? [hand]
  (contains-set-of-size? hand 4))

(defn flush? [hand]
  (let [suits (map suit hand)
        freqs (vals (frequencies suits))]
    (= freqs [5])))

(defn full-house? [hand]
  (and
    (contains-set-of-size? hand 3)
    (contains-set-of-size? hand 2)))

(defn two-pairs? [hand]
  (let [ranks (map rank hand)
        set-sizes-map (frequencies ranks)
        number-of-pairs (set-sizes-map 2)
        contains-four (four-of-a-kind? hand)]
    (or
      (= number-of-pairs 2)
      contains-four)))

(defn straight? [hand]
  (let [ranks (map rank hand)
        ranks-with-aces-as-ones (replace {14 1} ranks)]
    (or
      (apply < ranks-with-aces-as-ones)
      (apply < ranks))))

(defn straight-flush? [hand]
  (and
    (flush? hand)
    (straight? hand)))


(defn high-card? [hand]
  true)

(let [checkers #{[high-card? 0]
                 [pair? 1]
                 [two-pairs? 2]
                 [three-of-a-kind? 3]
                 [straight? 4]
                 [flush? 5]
                 [full-house? 6]
                 [four-of-a-kind? 7]
                 [straight-flush? 8]}])

(defn value [hand]
  0)
