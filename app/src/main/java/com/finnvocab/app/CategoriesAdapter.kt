package com.finnvocab.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// A sealed class to represent the different types of items in our list
sealed class DisplayableItem {
    data class Category(val name: String, val words: List<Word>, var isExpanded: Boolean = false) : DisplayableItem()
    data class WordItem(val word: Word) : DisplayableItem()
}

class CategoriesAdapter(private val items: MutableList<DisplayableItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private companion object {
        const val VIEW_TYPE_CATEGORY = 0
        const val VIEW_TYPE_WORD = 1
    }

    // ViewHolder for the Category Header
    inner class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categoryName: TextView = view.findViewById(R.id.tvCategoryName)
        val expandIcon: ImageView = view.findViewById(R.id.ivExpandIcon)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = items[position] as DisplayableItem.Category
                    toggleCategory(item, position)
                }
            }
        }
    }

    // ViewHolder for the Word Item
    inner class WordViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val finnishWord: TextView = view.findViewById(R.id.tvFinnishWord)
        val englishWord: TextView = view.findViewById(R.id.tvEnglishWord)
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is DisplayableItem.Category -> VIEW_TYPE_CATEGORY
            is DisplayableItem.WordItem -> VIEW_TYPE_WORD
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_CATEGORY) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category_header, parent, false)
            CategoryViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_word, parent, false)
            WordViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if (holder is CategoryViewHolder && item is DisplayableItem.Category) {
            holder.categoryName.text = item.name
            holder.expandIcon.setImageResource(
                if (item.isExpanded) R.drawable.ic_expand_less else R.drawable.ic_expand_more
            )
        } else if (holder is WordViewHolder && item is DisplayableItem.WordItem) {
            holder.finnishWord.text = item.word.finnish
            holder.englishWord.text = item.word.english
        }
    }

    override fun getItemCount(): Int = items.size

    private fun toggleCategory(category: DisplayableItem.Category, position: Int) {
        category.isExpanded = !category.isExpanded

        if (category.isExpanded) {
            // Add words to the list
            val wordsToAdd = category.words.map { DisplayableItem.WordItem(it) }
            items.addAll(position + 1, wordsToAdd)
            notifyItemRangeInserted(position + 1, wordsToAdd.size)
        } else {
            // Remove words from the list
            val wordsToRemove = category.words.size
            for (i in 0 until wordsToRemove) {
                items.removeAt(position + 1)
            }
            notifyItemRangeRemoved(position + 1, wordsToRemove)
        }
        // Update the icon
        notifyItemChanged(position)
    }
}
